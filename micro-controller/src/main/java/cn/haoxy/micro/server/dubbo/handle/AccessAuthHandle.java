package cn.haoxy.micro.server.dubbo.handle;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    /** HTTP Response 中的 Session ID 的名字 **/
    @Value("${session.SessionIdName}")
    private String sessionIdName;

    /** 反斜杠 */
    private static final String Back_Slash = "/";
    /** 星 */
    private static final String STAR = "*";

    /** 定义切点 **/
    @Pointcut("execution(public * cn.haoxy.micro.server.dubbo.controller..*.*(..))")
    public void accessAuth(){}

    @Before("accessAuth()")
    public void doBefore(){
       //访问鉴权
        authentication();
    }

    /**
     * 检查当前用户是否允许访问该接口
     */
    private void authentication() {


    }
}
