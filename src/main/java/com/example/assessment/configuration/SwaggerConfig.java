package com.example.assessment.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .securitySchemes(new ArrayList<>(Collections.singletonList(new ApiKey("Authorization", "Authorization", "header"))))
                .securityContexts(Collections.singletonList(securityContext()))
                .tags(new Tag("users", "Operations about users"))
                .tags(new Tag("stocks", "Operations about stocks"))
                .genericModelSubstitutes(Optional.class);
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()//
                .title("Fullstack Assessment API")
                .description("This is a the back-end service for the Fullstack Assessment application. It uses JWT for authentication & role management. Default Credentials: `user@email.com` or `admin@email.com`  (passwords are 'password') for testing purposes. Once you have successfully logged in and obtained the JWT, you should click on the right top button `Authorize` and introduce it with the prefix \"Bearer \".")
                .version("1.0.0")
                .contact(new Contact(null, null, "RamVakad@gmail.com"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

}
