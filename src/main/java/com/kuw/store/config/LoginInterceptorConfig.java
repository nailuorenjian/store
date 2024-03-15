package com.kuw.store.config;

import com.kuw.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 *  处理器拦截器的注册
 */
@Configuration //加载当前的拦截器并进行注册
public class LoginInterceptorConfig implements WebMvcConfigurer {
    /**
     *  配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建自定义的拦截器
        HandlerInterceptor interceptor = new LoginInterceptor();
        // 配置白名单，存放在一个List集合
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/product.html");
        patterns.add("/web/index.html");
        patterns.add("/users/insertuser");
        patterns.add("/users/login");
        patterns.add("/users/update");
        patterns.add("/district/**");
        patterns.add("/product/**");
        patterns.add("/products/**");
        patterns.add("/district/**");
        patterns.add("/");


        // 完成拦截器的注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);

    }

}
