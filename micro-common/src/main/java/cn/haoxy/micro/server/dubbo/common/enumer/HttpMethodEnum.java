package cn.haoxy.micro.server.dubbo.common.enumer;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 *
 * HTTP请求方式的枚举类
 */
public enum  HttpMethodEnum {

    POSTANDGET(0,"POST_GET"),
    GET(1,"GET"),
    POST(2,"POST"),
    PUT(3,"PUT"),
    DELETE(4,"DELETE");

    private int code;
    private String msg;

    HttpMethodEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
