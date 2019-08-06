package cn.haoxy.micro.server.dubbo.annotation;

import java.lang.annotation.*;

/**
 * @author Haoxy
 * Created in 2019-08-05.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
// 本注解只能用在方法上
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {

    // 是否需要登录（默认为true）
    boolean value() default true;

}
