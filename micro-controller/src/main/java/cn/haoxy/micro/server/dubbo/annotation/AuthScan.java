package cn.haoxy.micro.server.dubbo.annotation;

import java.lang.annotation.*;

/**
 * @author Haoxy
 * Created in 2019-08-05.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthScan {

    String value();
}
