package cn.haoxy.micro.server.dubbo.common.exception;

import java.io.Serializable;

import static cn.haoxy.micro.server.dubbo.common.utils.ExpPrefixUtil.ComExpPrefix;
import static cn.haoxy.micro.server.dubbo.common.utils.ExpPrefixUtil.UserExpPrefix;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 *
 * 全局的异常状态码 和 异常描述
 *
 * PS:异常码一共由5位组成，前两位为固定前缀，请参考{@link cn.haoxy.micro.server.dubbo.common.utils.ExpPrefixUtil}
 */
public enum ExpCodeEnum implements Serializable {

    /** 通用异常 */
    UNKNOW_ERROR(ComExpPrefix + "000", "未知异常"),
    ERROR_404(ComExpPrefix + "001", "没有该接口"),
    PARAM_NULL(ComExpPrefix + "002", "参数为空"),
    NO_REPEAT(ComExpPrefix + "003", "请勿重复提交"),
    SESSION_NULL(ComExpPrefix + "004", "请求头中SessionId不存在"),
    HTTP_REQ_METHOD_ERROR(ComExpPrefix + "005", "HTTP请求方法不正确"),
    JSONERROR(ComExpPrefix + "006", "JSON解析异常"),

    /** User模块异常 */
    USERNAME_NULL(UserExpPrefix + "000", "用户名为空"),
    PASSWD_NULL(UserExpPrefix + "001", "密码为空"),
    AUTH_NULL(UserExpPrefix + "002", "手机、电子邮件、用户名 至少填一个"),
    LOGIN_FAIL(UserExpPrefix + "003", "登录失败"),
    UNLOGIN(UserExpPrefix + "004", "尚未登录"),
    NO_PERMISSION(UserExpPrefix + "005", "没有权限"),
    PHONE_NULL(UserExpPrefix + "006", "手机号为空"),
    MAIL_NULL(UserExpPrefix + "007", "电子邮件为空");

    private String code;
    private String message;

    private ExpCodeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    ExpCodeEnum(){}

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
