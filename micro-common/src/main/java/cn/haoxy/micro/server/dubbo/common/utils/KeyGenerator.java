package cn.haoxy.micro.server.dubbo.common.utils;

import java.util.UUID;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class KeyGenerator {

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getKey(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
