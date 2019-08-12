package cn.haoxy.micro.server.dubbo.config;

import cn.haoxy.micro.server.dubbo.handle.AccessAuthHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Haoxy
 * Created in 2019-08-12.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessAuthHandle()).addPathPatterns("/**");
    }
    @Bean
    public AccessAuthHandle authenticationInterceptor() {
        return new AccessAuthHandle();
    }
}
