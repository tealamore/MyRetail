package com.myRetail.client

import com.myRetail.config.RedskyConfig
import com.myRetail.model.RedskyDetailsDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class RedskyClient {

    @Autowired
    private lateinit var redskyConfig: RedskyConfig

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    fun getProductName(id: Int): Mono<String> {
        return webClientBuilder
                .baseUrl(buildUrl(id))
                .build()
                .get()
                .exchange()
                .flatMap { it.toEntity(RedskyDetailsDto::class.java) }
                .map { it.body?.product?.item?.product_description?.title ?: "No Title" }
                .filter { it != "No Title" }
    }

    private fun buildUrl(id: Int) = "${redskyConfig.url}v2/pdp/tcin/$id"
}