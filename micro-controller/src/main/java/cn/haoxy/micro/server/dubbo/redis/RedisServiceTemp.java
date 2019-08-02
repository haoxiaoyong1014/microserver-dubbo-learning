package cn.haoxy.micro.server.dubbo.redis;

import cn.haoxy.micro.server.dubbo.common.entity.user.AccessAuthEntity;
import cn.haoxy.micro.server.dubbo.common.entity.user.UserEntity;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class RedisServiceTemp {

    public static Map<String, AccessAuthEntity> accessAuthMap = Maps.newHashMap();

    public static Map<String, UserEntity> userMap = Maps.newHashMap();
}
