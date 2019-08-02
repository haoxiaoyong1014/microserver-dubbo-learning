package cn.haoxy.micro.server.dubbo.common.utils;


import cn.haoxy.micro.server.dubbo.common.enumer.BaseEnum;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * @description
 */
public class EnumUtil {

    /**
     * 根据code获取枚举
     * @param enumClass
     * @param code
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据msg获取枚举
     * @param enumClass
     * @param msg
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseEnum> E msgOf(Class<E> enumClass, String msg) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getMsg().equals(msg)) {
                return e;
            }
        }
        return null;
    }



}
