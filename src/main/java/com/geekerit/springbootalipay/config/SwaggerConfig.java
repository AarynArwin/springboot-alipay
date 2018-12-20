package com.geekerit.springbootalipay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description swagger2在线展示接口文档配置类
 * @Author Aaryn
 * @Date 2018/9/30 14:29
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
@Profile(value = "dev")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 指定构建api文档的详细信息的方法：apiInfo()
                .apiInfo(apiInfo())
                .select()
                // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.geekerit.springbootalipay.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建api文档的详细信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("蚂蚁金服API集成")
                // 设置接口描述
                .description("Springboot集成蚂蚁金服开放API")
                // 设置联系方式
                .contact("GeekerHub，" + "https://blog.geekerit.com")
                // 设置版本
                .version("1.0")
                // 构建
                .build();
    }
}
