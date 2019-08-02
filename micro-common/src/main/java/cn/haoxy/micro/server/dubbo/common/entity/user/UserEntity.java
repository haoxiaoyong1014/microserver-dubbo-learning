package cn.haoxy.micro.server.dubbo.common.entity.user;

import cn.haoxy.micro.server.dubbo.common.enumer.user.UserStateEnum;
import cn.haoxy.micro.server.dubbo.common.enumer.user.UserTypeEnum;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class UserEntity implements Serializable {

    /** 主键 */

    private String id;

    /** 用户名 */

    private String username;

    /** 密码 */

    private String password;

    /** 手机号 */

    private String phone;

    /** 邮箱 */

    private String mail;

    /** 营业执照照片 */

    private String licencePic;

    /** 注册时间 */
    private Timestamp registerTime;

    /** 用户类型 {} */
    private UserTypeEnum userTypeEnum;

    /** 账号状态 {} */
    private UserStateEnum userStateEnum;

    /** 用户的角色 */
    private RoleEntity roleEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLicencePic() {
        return licencePic;
    }

    public void setLicencePic(String licencePic) {
        this.licencePic = licencePic;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public UserTypeEnum getUserTypeEnum() {
        return userTypeEnum;
    }

    public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
        this.userTypeEnum = userTypeEnum;
    }

    public UserStateEnum getUserStateEnum() {
        return userStateEnum;
    }

    public void setUserStateEnum(UserStateEnum userStateEnum) {
        this.userStateEnum = userStateEnum;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }



    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", licencePic='" + licencePic + '\'' +
                ", registerTime=" + registerTime +
                ", userTypeEnum=" + userTypeEnum +
                ", userStateEnum=" + userStateEnum +
                ", roleEntity=" + roleEntity +
                '}';
    }
}
