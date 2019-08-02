package cn.haoxy.micro.server.dubbo.common.enumer.user;

import cn.haoxy.micro.server.dubbo.common.enumer.BaseEnum;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public enum UserStateEnum implements BaseEnum {

    ON(1, "启用"),
    OFF(0, "禁用");

    private int code;
    private String msg;

    UserStateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "UserStateEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
