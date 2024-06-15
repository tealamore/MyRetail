package com.myRetail.model.response

import com.myRetail.model.PriceDetailsDto

data class ItemDetailsDto(val id: Int, val name: String, val current_price: CurrentPriceDto)

data class CurrentPriceDto(var value: Double, var currency_code: String) {
    constructor(priceDetailsDto: PriceDetailsDto) : this(priceDetailsDto.value, priceDetailsDto.currency_code)
}