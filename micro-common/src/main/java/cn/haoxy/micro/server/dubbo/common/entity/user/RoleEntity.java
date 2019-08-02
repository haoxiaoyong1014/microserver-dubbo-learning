package cn.haoxy.micro.server.dubbo.common.entity.user;

import java.io.Serializable;
import java.util.List;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 *
 * 角色实体类
 */
public class RoleEntity implements Serializable {

    /** 主键 */

    private String id;

    /** 角色名称 */

    private String name;

    /** 角色描述 */

    private String desc;

    /** 该角色能访问的菜单 */

    private List<MenuEntity> menuList;

    /** 该角色拥有的权限 */
    private List<PermissionEntity> permissionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<MenuEntity> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuEntity> menuList) {
        this.menuList = menuList;
    }

    public List<PermissionEntity> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PermissionEntity> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", menuList=" + menuList +
                ", permissionList=" + permissionList +
                '}';
    }
}
