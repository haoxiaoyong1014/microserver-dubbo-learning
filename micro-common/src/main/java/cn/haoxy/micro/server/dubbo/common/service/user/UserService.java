package cn.haoxy.micro.server.dubbo.common.service.user;

import cn.haoxy.micro.server.dubbo.common.entity.user.UserEntity;
import cn.haoxy.micro.server.dubbo.common.req.user.LoginReq;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface UserService {


    UserEntity login(LoginReq loginReq);

}
