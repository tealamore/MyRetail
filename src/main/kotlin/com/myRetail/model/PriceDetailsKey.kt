package com.myRetail.model

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn

@PrimaryKeyClass
data class PriceDetailsKey(@field:PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED) var id: Int)