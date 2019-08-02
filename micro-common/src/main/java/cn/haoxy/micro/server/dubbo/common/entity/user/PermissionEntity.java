package cn.haoxy.micro.server.dubbo.common.entity.user;

import java.io.Serializable;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 *
 * 权限实体类
 */
public class PermissionEntity implements Serializable {

    /** 主键 */

    private String id;

    /** 权限名称 */

    private String permission;

    /** 权限描述 */

    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "PermissionEntity{" +
                "id='" + id + '\'' +
                ", permission='" + permission + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
