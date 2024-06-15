package com.myRetail.exception

data class ItemNotFoundException(val itemId: Int) : Exception()