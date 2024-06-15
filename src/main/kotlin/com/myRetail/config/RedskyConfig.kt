package com.myRetail.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("redsky")
data class RedskyConfig(var url: String = "")