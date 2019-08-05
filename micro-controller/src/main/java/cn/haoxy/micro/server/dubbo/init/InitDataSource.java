package cn.haoxy.micro.server.dubbo.init;

import cn.haoxy.micro.server.dubbo.annotation.AuthScan;
import cn.haoxy.micro.server.dubbo.common.utils.AnnotationUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoxy
 * Created in 2019-08-05.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@Component
@AuthScan("cn.haoxy.micro.server.dubbo.controller")
public class InitDataSource implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        loadAccessAuth();
    }

    private void loadAccessAuth() {
        // 第一个class类的集合

        //获取自定类上的指定注解
        AuthScan authScan = AnnotationUtil.getAnnotationValueByClass(this.getClass(), AuthScan.class);
        String pkName = authScan.value();
        //获取包下所有的类
        List<Class<?>> classes = getClass(pkName);
        if (CollectionUtils.isEmpty(classes)) {
            return;
        }
        for (Class<?> clazz : classes) {
            System.out.println(clazz);
        }
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
}
