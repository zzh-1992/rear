/*
 *Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger config
 *
 * @Author ZhangZhihuang
 * @Date 2022/10/10 21:14
 * @Version 1.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("category-api")
                .apiInfo(apiInfo())
                .select()
                .paths(any())
                .build()
                .ignoredParameterTypes(ApiIgnore.class)
                .enableUrlTemplating(true);
    }

    private static Predicate<String> any() {
        return (each) -> true;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Rear Service API")
                .description("my api")
                .termsOfServiceUrl("http://springfox.io")
                .contact(new Contact("springfox", "", ""))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
}
