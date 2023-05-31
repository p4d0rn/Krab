package com.krab.burger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    private static ApiInfo apiInfo;

    static {
        apiInfo = new ApiInfoBuilder()
                .title("Krusty Krab API Document")
                .description("Enjoy Our Yummy api hamburger")
                .contact(new Contact("p4d0rn", "http://www.p4d0rn.com", "p4d0rn@qq.com"))
                .version("0.0")
                .build();
    }

    @Bean(value = "dockerBean")
    public Docket dockerBean(){
        // Swagger2规范
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("front api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.krab.burger.controller"))  // 设置扫描包路径
                .paths(PathSelectors.any())  // 路径筛选, 这里为包下的任何接口
                .build();
    }

}
