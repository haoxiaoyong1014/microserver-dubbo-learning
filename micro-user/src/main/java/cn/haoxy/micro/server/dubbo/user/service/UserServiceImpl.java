package cn.haoxy.micro.server.dubbo.user.service;

import cn.haoxy.micro.server.dubbo.common.entity.user.UserEntity;
import cn.haoxy.micro.server.dubbo.common.exception.CommonBizException;
import cn.haoxy.micro.server.dubbo.common.exception.ExpCodeEnum;
import cn.haoxy.micro.server.dubbo.common.req.user.LoginReq;
import cn.haoxy.micro.server.dubbo.common.req.user.UserQueryReq;
import cn.haoxy.micro.server.dubbo.common.service.user.UserService;
import cn.haoxy.micro.server.dubbo.user.dao.UserDAO;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service(version = "v1.0.0")
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;
    @Override
    public UserEntity login(LoginReq loginReq) {


        //检查参数
        checkParam(loginReq);
        //创建用户查询请求
        UserQueryReq userQueryReq = buildUserQueryReq(loginReq);

        // 查询用户
        List<UserEntity> userEntityList = userDAO.findUsers(userQueryReq);

        // 查询失败
        if (CollectionUtils.isEmpty(userEntityList)) {
            throw new CommonBizException(ExpCodeEnum.LOGIN_FAIL);
        }
        // 查询成功
        return userEntityList.get(0);

    }

    private UserQueryReq buildUserQueryReq(LoginReq loginReq) {
        UserQueryReq userQueryReq = new UserQueryReq();
        // 设置密码
        userQueryReq.setPassword(loginReq.getPassword());
        // 设置用户名
        if (StringUtils.isNotEmpty(loginReq.getUsername())) {
            userQueryReq.setUsername(loginReq.getUsername());
        }
        // 设置邮箱
        if (StringUtils.isNotEmpty(loginReq.getMail())) {
            userQueryReq.setMail(loginReq.getMail());
        }
        // 设置手机号
        if (StringUtils.isNotEmpty(loginReq.getPhone())) {
            userQueryReq.setPhone(loginReq.getPhone());
        }
        return userQueryReq;
    }

    private void checkParam(LoginReq loginReq) {
        //密码不能为空
        if (StringUtils.isEmpty(loginReq.getPassword())) {

            throw new CommonBizException(ExpCodeEnum.PASSWD_NULL);
        }

        //手机,mail,用户名至少填一个
        if (StringUtils.isEmpty(loginReq.getUsername())
                && StringUtils.isEmpty(loginReq.getMail())
                && StringUtils.isEmpty(loginReq.getPhone())) {
            throw new CommonBizException(ExpCodeEnum.AUTH_NULL);
        }

    }
}
