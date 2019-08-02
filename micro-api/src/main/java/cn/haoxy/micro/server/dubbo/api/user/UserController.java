package cn.haoxy.micro.server.dubbo.api.user;

import cn.haoxy.micro.server.dubbo.common.req.user.LoginReq;
import cn.haoxy.micro.server.dubbo.common.rsp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@Api(value = "用户接口", description = "关于用户相关接口")
public interface UserController {


     @ApiOperation(value = "用户登录接口",notes="手机号或邮箱")
     Result login(@RequestBody LoginReq loginReq, HttpServletResponse httpRsp);

}
