package com.myRetail.service

import com.myRetail.client.RedskyClient
import com.myRetail.dao.PriceDetailsDao
import com.myRetail.model.PriceDetailsDto
import com.myRetail.model.PriceDetailsKey
import com.myRetail.model.request.UpdateItemPriceDetailsDto
import com.myRetail.model.response.CurrentPriceDto
import com.myRetail.model.response.ItemDetailsDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RetailService {
    @Autowired
    private lateinit var redskyClient: RedskyClient
    @Autowired
    private lateinit var priceDetailsDao: PriceDetailsDao

    fun getItem(id: Int): Mono<ItemDetailsDto> {
        return redskyClient.getProductName(id)
                .zipWith(priceDetailsDao.findById(PriceDetailsKey(id)))
                .map { ItemDetailsDto(id, name = it.t1, current_price = CurrentPriceDto(priceDetailsDto = it.t2)) }
    }

    fun updateItemPriceDetails(id: Int, updateItemPriceDetailsDto: UpdateItemPriceDetailsDto): Mono<UpdateItemPriceDetailsDto> {
        val priceDetailsDto = PriceDetailsDto(id, updateItemPriceDetailsDto)

        return priceDetailsDao.save(priceDetailsDto)
                                .map { UpdateItemPriceDetailsDto(it) }
    }

}