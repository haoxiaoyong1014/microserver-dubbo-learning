package cn.haoxy.micro.server.dubbo.user.dao;

import cn.haoxy.micro.server.dubbo.common.entity.user.MenuEntity;
import cn.haoxy.micro.server.dubbo.common.entity.user.PermissionEntity;
import cn.haoxy.micro.server.dubbo.common.entity.user.RoleEntity;
import cn.haoxy.micro.server.dubbo.common.entity.user.UserEntity;
import cn.haoxy.micro.server.dubbo.common.req.user.UserQueryReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Mapper
public interface UserDAO {

    /**
     * 查询用户信息
     * @param userQueryReq 查询请求
     * @return 查询结果
     */
    List<UserEntity> findUsers(@Param("userQueryReq") UserQueryReq userQueryReq);

    /**
     * 插入用户信息
     * @param userEntity 用户信息
     */
    void createUser(UserEntity userEntity);

    /**
     * 批量更新用户状态
     * @param userStateCode 用户状态
     * @param userIdList 用户ID列表
     */
    void batchUpdateUserState(@Param("userStateCode") Integer userStateCode,
                              @Param("userIdList") List<String> userIdList);

    /**
     * 查询所有的角色
     * @return 角色列表
     */
    List<RoleEntity> findRoles();

    /**
     * 删除角色
     * @param roleId 角色ID
     */
    void deleteRole(String roleId);

    /**
     * 删除角色-权限关系
     * @param roleId 角色ID
     */
    void deleteRolePermission(String roleId);

    /**
     * 删除角色-菜单关系
     * @param roleId 角色ID
     */
    void deleteRoleMenu(String roleId);



    /**
     * 查询所有权限
     * @return 权限列表
     */
    List<PermissionEntity> findPermissions();

    /**
     * 查询所有菜单
     * @return 菜单列表
     */
    List<MenuEntity> findMenus();



    void deleteLocation(@Param("locationId") String locationId, @Param("userId") String userId);

}
