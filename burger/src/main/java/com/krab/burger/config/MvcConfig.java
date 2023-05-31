package com.krab.burger.config;

import com.krab.burger.interceptors.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/captcha")
                .excludePathPatterns("/backend/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/swagger/**")
                .excludePathPatterns("/v2/api-docs-ext/**")
                .excludePathPatterns("/swagger-resources/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");

        /** 配置knife4j 显示文档 */
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        /**
         * 配置swagger-ui显示文档
         */
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        /** 公共部分内容 */
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    // 拦截器跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins("*")
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
