package com.myRetail.controller

import com.myRetail.exception.ItemNotFoundException
import com.myRetail.model.request.UpdateItemPriceDetailsDto
import com.myRetail.model.response.ItemDetailsDto
import com.myRetail.service.RetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("products")
class RetailController {
    @Autowired
    private lateinit var retailService: RetailService

    @GetMapping(value = ["/{id}"])
    fun getItem(@PathVariable("id") id: Int): Mono<ItemDetailsDto> {
        return retailService.getItem(id)
                .switchIfEmpty(Mono.error(ItemNotFoundException(id)))
    }

    @PostMapping(value = ["/{id}"], consumes = ["application/json"])
    fun updateItemPriceDetails(@PathVariable("id") id: Int,
                               @RequestBody priceDetailsDto: UpdateItemPriceDetailsDto): Mono<UpdateItemPriceDetailsDto> {
        return retailService.updateItemPriceDetails(id, priceDetailsDto)
                .switchIfEmpty(Mono.error(ItemNotFoundException(id)))
    }
}