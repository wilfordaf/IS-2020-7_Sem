package ru.quipy.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

const val CONTROLLERS_BASE_PACKAGE = "ru.quipy.controllers"

@Configuration
@EnableSwagger2
class SwaggerConfiguration

@Bean
fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(CONTROLLERS_BASE_PACKAGE))
        .paths(PathSelectors.any())
        .build()
}