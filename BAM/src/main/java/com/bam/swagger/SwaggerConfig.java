package com.bam.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bam.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    public ApiInfo metaDat() {
        ApiInfo info = new ApiInfo(
                "Bam Catalog API",
                "BAM Catalog API for Library Services",
                "1.0.0",
                "Terms of Service",
                new Contact("theara", "someURL", "email"),
                "Apache License Version 2.0",
                "https://apache.org/licenses/LICENSE-2.0");
        return info;
    }
}
