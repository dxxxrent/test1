package com.gxyan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    /**
     * 配置跨域请求
     * @param registry 跨域注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径的跨域请求
                .allowedOrigins(ALL) // 允许所有来源
                .allowedMethods(ALL) // 允许所有HTTP方法
                .allowedHeaders(ALL) // 允许所有请求头
                .allowCredentials(true); // 允许携带凭证
    }
}
