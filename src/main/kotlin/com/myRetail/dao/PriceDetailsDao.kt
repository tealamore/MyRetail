package com.myRetail.dao

import com.myRetail.model.PriceDetailsDto
import com.myRetail.model.PriceDetailsKey
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface PriceDetailsDao : ReactiveCassandraRepository<PriceDetailsDto, PriceDetailsKey>