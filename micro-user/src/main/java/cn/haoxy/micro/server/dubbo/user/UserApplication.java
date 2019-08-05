package cn.haoxy.micro.server.dubbo.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@SpringBootApplication
@MapperScan("cn.haoxy.micro.server.dubbo.user.dao")
@EnableDubbo
public class UserApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class,args);
    }
}
