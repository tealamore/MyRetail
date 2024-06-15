package com.myRetail

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiInfo.DEFAULT_CONTACT
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import com.fasterxml.classmate.TypeResolver
import com.myRetail.model.request.UpdateItemPriceDetailsDto
import com.myRetail.model.response.ItemDetailsDto
import springfox.documentation.schema.AlternateTypeRule
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableSwagger2
class MyRetailApplication {

    @Autowired
    private lateinit var typeResolver: TypeResolver

    private lateinit var monoItemDetailsRule: AlternateTypeRule

    private lateinit var monoUpdateItemPriceDetailsDto: AlternateTypeRule

    @PostConstruct
    fun postConstruct() {
        monoItemDetailsRule = AlternateTypeRule(
                typeResolver.resolve(Mono::class.java, ItemDetailsDto::class.java),
                typeResolver.resolve(ItemDetailsDto::class.java)
        )

        monoUpdateItemPriceDetailsDto = AlternateTypeRule(
                typeResolver.resolve(Mono::class.java, UpdateItemPriceDetailsDto::class.java),
                typeResolver.resolve(UpdateItemPriceDetailsDto::class.java)
        )
    }

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }

    @Bean
    fun docket(@Value("\${swagger.title}") swaggerTitle: String,
               @Value("\${swagger.description}") swaggerDescription: String,
               @Value("\${info.app.version}") version: String): Docket =
            Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myRetail.controller"))
                .build()
                .apiInfo(ApiInfo(swaggerTitle, swaggerDescription, version, "", DEFAULT_CONTACT,
                        "", "", listOf()))
                .alternateTypeRules(monoItemDetailsRule, monoUpdateItemPriceDetailsDto)
}

fun main(args: Array<String>) {
    runApplication<MyRetailApplication>(*args)
}
