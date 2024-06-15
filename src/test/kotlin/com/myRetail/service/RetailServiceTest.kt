package com.myRetail.service

import com.myRetail.client.RedskyClient
import com.myRetail.dao.PriceDetailsDao
import com.myRetail.model.PriceDetailsDto
import com.myRetail.model.PriceDetailsKey
import com.myRetail.model.request.UpdateItemPriceDetailsDto
import com.myRetail.model.response.CurrentPriceDto
import com.myRetail.model.response.ItemDetailsDto
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
class RetailServiceTest {
    @Mock
    lateinit var redskyClient: RedskyClient
    @Mock
    lateinit var priceDetailsDao: PriceDetailsDao

    @InjectMocks
    lateinit var retailService: RetailService

    @Test
    fun `should combine the results of redskyClient and priceDetailsDao when they succeed`() {
        val expectedResponse = ItemDetailsDto(6, "testName", CurrentPriceDto(0.5, "test currency"))

        `when`(redskyClient.getProductName(6)).thenReturn(Mono.just("testName"))
        `when`(priceDetailsDao.findById(PriceDetailsKey(6))).thenReturn(Mono.just(PriceDetailsDto(PriceDetailsKey(6), 0.5, "test currency")))

        val response = retailService.getItem(6).block()

        assertThat<ItemDetailsDto>(response, `is`<ItemDetailsDto>(expectedResponse))
    }

    @Test
    fun `should return mono of null when redskyClient fails`() {
        `when`(redskyClient.getProductName(6)).thenReturn(Mono.empty())
        `when`(priceDetailsDao.findById(PriceDetailsKey(6))).thenReturn(Mono.just(PriceDetailsDto(PriceDetailsKey(6), 0.5, "test currency")))

        val response = retailService.getItem(6).block()

        assertThat<ItemDetailsDto>(response, nullValue())
    }

    @Test
    fun `should return mono of null when priceDetailsDao fails`() {
        `when`(redskyClient.getProductName(6)).thenReturn(Mono.just("testName"))
        `when`(priceDetailsDao.findById(PriceDetailsKey(6))).thenReturn(Mono.empty())

        val response = retailService.getItem(6).block()

        assertThat<ItemDetailsDto>(response, nullValue())
    }

    @Test
    fun `should return mono of null when redsky and priceDetailsDao fail`() {
        `when`(redskyClient.getProductName(6)).thenReturn(Mono.empty())
        `when`(priceDetailsDao.findById(PriceDetailsKey(6))).thenReturn(Mono.empty())

        val response = retailService.getItem(6).block()

        assertThat<ItemDetailsDto>(response, nullValue())
    }

    @Test
    fun `should return passed in value for dao save`() {
        val updateItemPriceDetailsDto = UpdateItemPriceDetailsDto(0.5, "test currency")
        val priceDetailsDto = PriceDetailsDto(6, updateItemPriceDetailsDto)

        `when`(priceDetailsDao.save(priceDetailsDto)).thenReturn(Mono.just(priceDetailsDto))

        val response = retailService.updateItemPriceDetails(6, updateItemPriceDetailsDto).block()

        assertThat<UpdateItemPriceDetailsDto>(response, `is`<UpdateItemPriceDetailsDto>(updateItemPriceDetailsDto))
    }

    @Test
    fun `should return mono of null when dao save fails`() {
        val updateItemPriceDetailsDto = UpdateItemPriceDetailsDto(0.5, "test currency")
        val priceDetailsDto = PriceDetailsDto(6, updateItemPriceDetailsDto)

        `when`(priceDetailsDao.save(priceDetailsDto)).thenReturn(Mono.empty())

        val response = retailService.updateItemPriceDetails(6, updateItemPriceDetailsDto).block()

        assertThat<UpdateItemPriceDetailsDto>(response, nullValue())
    }
}