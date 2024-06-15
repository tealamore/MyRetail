package com.myRetail.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties
data class RedskyDetailsDto(val product: Product)

@JsonIgnoreProperties
data class Product(val item: Item)

@JsonIgnoreProperties
data class Item(val product_description: ProductDescription)

@JsonIgnoreProperties
data class ProductDescription(val title: String)
