package cn.haoxy.micro.server.dubbo.handle;

import cn.haoxy.micro.server.dubbo.common.entity.user.AccessAuthEntity;
import cn.haoxy.micro.server.dubbo.common.entity.user.UserEntity;
import cn.haoxy.micro.server.dubbo.common.exception.CommonBizException;
import cn.haoxy.micro.server.dubbo.common.exception.ExpCodeEnum;
import cn.haoxy.micro.server.dubbo.redis.RedisServiceTemp;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Haoxy
 * Created in 2019-08-07.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * <p>
 * 访问权限处理类(所有请求都要经过此类)
 */
@Aspect
@Component
public class AccessAuthHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * HTTP Response 中的 Session ID 的名字
     **/
    @Value("${session.SessionIdName}")
    private String sessionIdName;

    /**
     * 反斜杠
     */
    private static final String Back_Slash = "/";
    /**
     * 星
     */
    private static final String STAR = "*";

    /**
     * 定义切点
     **/
    @Pointcut("execution(public * cn.haoxy.micro.server.dubbo.controller..*.*(..))")
    public void accessAuth() {
    }

    @Before("accessAuth()")
    public void doBefore() {
        //访问鉴权
        authentication();
    }

    /**
     * 检查当前用户是否允许访问该接口
     */
    private void authentication() {
        //获取 HttpServletRequest
        HttpServletRequest request = getHttpServletRequest();
        String method = request.getMethod();
        String url = request.getServletPath();
        //获取 SessionID
        String sessionID = getSessionID(request);
        //获取 SessionID对应的用户信息
        UserEntity userEntity = getUserEntity(sessionID);
        //获取接口权限信息
        AccessAuthEntity accessAuthEntity = getAccessAuthEntity(method, url);

    }

    /**
     * 获取接口的权限信息
     *
     * @param method 请求方式
     * @param url    请求路径
     * @return 该接口对应的权限信息
     */
    private AccessAuthEntity getAccessAuthEntity(String method, String url) {
        Map<String, AccessAuthEntity> accessAuthMap = RedisServiceTemp.accessAuthMap;
        if (accessAuthMap != null && accessAuthMap.size() > 0) {
            for (String key : accessAuthMap.keySet()) {
                if(isMatch(key,method,url)){
                    return accessAuthMap.get(key);
                }
            }
        }
        // 没有该接口
        throw new CommonBizException(ExpCodeEnum.ERROR_404);
    }
    /**
     * 判断key是否与method+url匹配
     * @param key 接口的 "method + url"
     * @param method
     * @param url
     * @return
     */
    private boolean isMatch(String key, String method, String url) {

        // 清除首尾的反斜杠
        if (url.startsWith(Back_Slash)) {
            url = url.substring(1);
        }
        if (url.endsWith(Back_Slash)) {
            url = url.substring(0,url.length()-1);
        }

        // 将URL按照反斜杠切分
        String[] urls_1 = key.split("/");
        String[] urls_2 = url.split("/");
        urls_2[0] = method + urls_2[0];

        // 反斜杠数量不同，则匹配失败
        if (urls_1.length != urls_2.length) {
            return false;
        }

        // 逐个匹配
        for (int i=0; i<urls_1.length; i++) {
            // 若当前是个* 或 当前字符串相等，则匹配下一个
            if (STAR.equals(urls_1[i]) || urls_1[i].equals(urls_2[i])) {
                continue;
            }

            // 若两个字符串不相等，则匹配失败
            return false;
        }

        // 匹配成功
        return true;
    }
    /**
     * 获取SessionID对应的用户信息
     *
     * @param sessionID
     * @return
     */
    private UserEntity getUserEntity(String sessionID) {
        if (StringUtils.isEmpty(sessionID)) {
            return null;
        }
        //获取 UserEntuty
        UserEntity userEntity = RedisServiceTemp.userMap.get(sessionID);
        if (userEntity == null) {
            return null;
        }
        return (UserEntity) userEntity;
    }

    /**
     * 获取SessionID
     *
     * @param request 当前的请求对象
     * @return SessionID的值
     */

    private String getSessionID(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        //遍历所有 cookie,找出 SessionID
        String sessionID = null;
        for (Cookie cookie : cookies) {
            if (sessionIdName.equals(cookie.getName())) {
                sessionID = cookie.getValue();
                break;
            }
        }
        return sessionID;
    }

    /**
     * 获取 HttpServletRequest
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
