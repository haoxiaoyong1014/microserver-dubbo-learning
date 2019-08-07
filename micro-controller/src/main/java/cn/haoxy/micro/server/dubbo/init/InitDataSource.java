package cn.haoxy.micro.server.dubbo.init;

import cn.haoxy.micro.server.dubbo.annotation.AuthScan;
import cn.haoxy.micro.server.dubbo.annotation.Login;
import cn.haoxy.micro.server.dubbo.annotation.Permission;
import cn.haoxy.micro.server.dubbo.common.entity.user.AccessAuthEntity;
import cn.haoxy.micro.server.dubbo.common.enumer.HttpMethodEnum;
import cn.haoxy.micro.server.dubbo.common.utils.AnnotationUtil;
import cn.haoxy.micro.server.dubbo.redis.RedisServiceTemp;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Haoxy
 * Created in 2019-08-05.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@Component
@AuthScan("cn.haoxy.micro.server.dubbo.controller")
public class InitDataSource implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 反斜杠
     */
    private static final String Back_Slash = "/";

    /**
     * 接口权限列表
     **/

    private Map<String, AccessAuthEntity> accessAuthMap = Maps.newHashMap();

    @Override
    public void run(String... args) throws Exception {
        loadAccessAuth();
    }

    private void loadAccessAuth() {

        //获取自定类上的指定注解
        AuthScan authScan = AnnotationUtil.getAnnotationValueByClass(this.getClass(), AuthScan.class);
        String pkName = authScan.value();
        //获取包下所有的类
        List<Class<?>> classes = getClass(pkName);
        if (CollectionUtils.isEmpty(classes)) {
            return;
        }
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getMethods();
            if (methods.length == 0 || methods == null) {
                continue;
            }
            for (Method method : methods) {
                AccessAuthEntity accessAuthEntity = buildAccessAuthEntity(method);
                if (accessAuthEntity != null) {
                    //生成key
                    String key = generateKey(accessAuthEntity);
                    //暂存存本地
                    accessAuthMap.put(key, accessAuthEntity);
                    logger.debug("", accessAuthEntity);
                }
            }
        }
        //暂存本地
        RedisServiceTemp.accessAuthMap = accessAuthMap;
        logger.info("接口访问权限已加载完毕！" + accessAuthMap);
        logger.info("接口访问权限已加载完毕！" + accessAuthMap.size());
    }

    /**
     * 生成接口权限信息的Key
     * Key = 'AUTH'+请求方式+请求URL
     *
     * @param accessAuthEntity 接口权限信息
     * @return Key
     */
    private String generateKey(AccessAuthEntity accessAuthEntity) {
        return accessAuthEntity.getHttpMethodEnum().getMsg() +
                accessAuthEntity.getUrl();
    }

    private AccessAuthEntity buildAccessAuthEntity(Method method) {

        //获取指定方法上的指定注解
        RequestMapping requestMapping = AnnotationUtil.getAnnotationValueByMethod(method, RequestMapping.class);
        GetMapping getMapping = AnnotationUtil.getAnnotationValueByMethod(method, GetMapping.class);
        PostMapping postMapping = AnnotationUtil.getAnnotationValueByMethod(method, PostMapping.class);
        AccessAuthEntity accessAuthEntity = null;
        if (requestMapping != null
                && requestMapping.value() != null
                && requestMapping.value().length == 1
                && StringUtils.isNotEmpty(requestMapping.value()[0])) {
            accessAuthEntity = new AccessAuthEntity();
            accessAuthEntity.setHttpMethodEnum(HttpMethodEnum.POSTANDGET);
            accessAuthEntity.setMethodName(method.getName());
            accessAuthEntity.setUrl(trimUrl(requestMapping.value()[0]));
        } else if (getMapping != null
                && getMapping.value() != null
                && getMapping.value().length == 1
                && StringUtils.isNotEmpty(getMapping.value()[0])) {
            accessAuthEntity = new AccessAuthEntity();
            accessAuthEntity.setHttpMethodEnum(HttpMethodEnum.GET);
            accessAuthEntity.setMethodName(method.getName());
            accessAuthEntity.setUrl(trimUrl(getMapping.value()[0]));
        } else if (postMapping != null
                && postMapping.value() != null
                && postMapping.value().length == 1
                && StringUtils.isNotEmpty(postMapping.value()[0])) {
            accessAuthEntity = new AccessAuthEntity();
            accessAuthEntity.setHttpMethodEnum(HttpMethodEnum.POST);
            accessAuthEntity.setMethodName(method.getName());
            accessAuthEntity.setUrl(trimUrl(postMapping.value()[0]));
        }
        //解析自定义的注解 例如 @Login 和 @Permission
        if (accessAuthEntity != null) {
            accessAuthEntity = getLoginAndPermission(method, accessAuthEntity);
            return accessAuthEntity;
        }
        return null;
    }

    private AccessAuthEntity getLoginAndPermission(Method method, AccessAuthEntity accessAuthEntity) {
        // 获取@Permission的值
        Permission permission = AnnotationUtil.getAnnotationValueByMethod(method, Permission.class);
        if (permission != null && StringUtils.isNotEmpty(permission.value())) {
            accessAuthEntity.setPermission(permission.value());
            accessAuthEntity.setLogin(true);
            return accessAuthEntity;
        }
        //获取@Login的值
        Login login = AnnotationUtil.getAnnotationValueByMethod(method, Login.class);
        if (login != null) {
            accessAuthEntity.setLogin(true);
            return accessAuthEntity;
        }
        accessAuthEntity.setLogin(false);
        return accessAuthEntity;
    }

    private List<Class<?>> getClass(String pkName) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        URL url = this.getClass().getClassLoader().getResource(pkName.replace(".", "/"));
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            //获取包的物理路径
            try {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                //以文件的形式扫描整个包下的文件,并添加到集合中
                findAndAddClassesInPackageByFile(pkName, filePath, classes);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private void findAndAddClassesInPackageByFile(String pkName, String filePath, List<Class<?>> classes) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在就获取包下的所有文件,包括目录
        File[] dirFiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirFiles) {
            //如果是目录,则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(pkName + "." + file.getName(), file.getAbsolutePath(), classes);
            } else {
                String className = pkName + "." + file.getName().replaceAll(".class", "");
                try {
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理URL
     * 1. 将URL两侧的斜杠去掉
     * 2. 将URL中的"{xxx}"替换为"*"
     *
     * @param url 原始URL
     * @return 处理后的URL
     */
    private static String trimUrl(String url) {
        // 清除首尾的反斜杠
        if (url.startsWith(Back_Slash)) {
            url = url.substring(1);
        }
        if (url.endsWith(Back_Slash)) {
            url = url.substring(0, url.length() - 1);
        }

        // 将"{xxx}"替换为"*"
        // TODO 正则表达式要继续完善（纠正/user/{xxxxx}/{yyyy}——>user/*的情况）
        url = url.replaceAll("\\{(.*)\\}", "*");
        return url;
    }

    public static void main(String[] args) {
        System.out.println("user:" + trimUrl("user"));
        System.out.println("{}:" + trimUrl("{}"));
        System.out.println("/user:" + trimUrl("/user"));
        System.out.println("/user/:" + trimUrl("/user/"));
        System.out.println("user/{xxxx}:" + trimUrl("user/{xxxx}"));
        System.out.println("/user/{xxxxx}/{yyyy}:" + trimUrl("/user/{xxxxx}/{yyyy}"));
        System.out.println("/user/home/{sdsds}:" + trimUrl("/user/home/{sdsds}"));
        System.out.println("/user/{home}/{zzzzz}/:" + trimUrl("/user/{home}/{zzzzz}/"));
        System.out.println("/user/home/zzzzz/:" + trimUrl("/user/home/zzzzz/"));
    }
}
