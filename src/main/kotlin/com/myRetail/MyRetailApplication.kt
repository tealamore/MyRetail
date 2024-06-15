package com.myRetail

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class MyRetailApplication

fun main(args: Array<String>) {
    runApplication<MyRetailApplication>(*args)
}

@Bean
fun webClientBuilder(): WebClient.Builder {
    return WebClient.builder()
}
