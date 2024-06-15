package com.myRetail.model.request

import com.myRetail.model.PriceDetailsDto

data class UpdateItemPriceDetailsDto(val id: Int, val value: Double, val currency_code: String) {
    constructor(priceDetailsDto: PriceDetailsDto) :
            this(priceDetailsDto.id.id, priceDetailsDto.value, priceDetailsDto.currency_code)
}