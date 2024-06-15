package com.myRetail.model.request

import com.myRetail.model.PriceDetailsDto

data class UpdateItemPriceDetailsDto(val value: Double, val currency_code: String) {
    constructor(priceDetailsDto: PriceDetailsDto) :
            this(priceDetailsDto.value, priceDetailsDto.currency_code)
}