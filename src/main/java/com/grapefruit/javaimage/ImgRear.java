/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

@EnableSwagger2
//@EnableOpenApi
@MapperScan(basePackages = "com.grapefruit.javaimage.mapper") //或是使用注解@Mapper
@EnableTransactionManagement
//@RefreshScope
@NacosPropertySource(dataId = "${nacos.config.data-id}")
@SpringBootApplication
public class ImgRear {
    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(ImgRear.class, args);

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        // 生成api文档(markdown格式)
        Swagger2MarkupConverter.from(new URL("http://localhost:8888/v2/api-docs?group=category-api"))
                .withConfig(config).build().toFile(Paths.get("src/main/resources/api-docs"));
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
