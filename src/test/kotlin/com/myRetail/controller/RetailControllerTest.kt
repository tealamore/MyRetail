package com.myRetail.controller

import com.myRetail.exception.ItemNotFoundException
import com.myRetail.model.request.UpdateItemPriceDetailsDto
import com.myRetail.model.response.CurrentPriceDto
import com.myRetail.model.response.ItemDetailsDto
import com.myRetail.service.RetailService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
class RetailControllerTest {
    @Mock
    private lateinit var service: RetailService

    @InjectMocks
    private lateinit var controller: RetailController

    @Test
    fun `returns response from service getItem`() {
        try {
            val expectedResponse = ItemDetailsDto(6, "testName",
                                        CurrentPriceDto(0.0, "Test currency"))
            `when`(service.getItem(6)).thenReturn(Mono.just(expectedResponse))

            val response = controller.getItem(6).block()

            assertThat<ItemDetailsDto>(response, `is`<ItemDetailsDto>(expectedResponse))
        } catch (e: Exception) {
            assert(false)
        }
    }

    @Test
    fun `returns response from service getItem for failure`() {
        try {
            val serviceResponse = Mono.empty<ItemDetailsDto>()

            `when`(service.getItem(6)).thenReturn(serviceResponse)

            controller.getItem(6).block()

            assert(false)
        } catch (e: Exception) {
            assertThat<Throwable>(e.cause, `is`<Throwable>(ItemNotFoundException(6)))
        }
    }

    @Test
    fun `returns response from service updateItemPriceDetails`() {
        try {
            val request = UpdateItemPriceDetailsDto(6, 0.5, "USD")
            val expectedResponse = request
            val serviceResponse = Mono.just(request)
            `when`(service.updateItemPriceDetails(request)).thenReturn(serviceResponse)

            val response = controller.updateItemPriceDetails(request).block()

            assertThat<UpdateItemPriceDetailsDto>(response, `is`<UpdateItemPriceDetailsDto>(expectedResponse))
        } catch (e: Exception) {
            assert(false)
        }
    }

    @Test
    fun `returns response from service updateItemPriceDetails for failure`() {
        try {
            val request = UpdateItemPriceDetailsDto(6, 0.5, "USD")
            val serviceResponse = Mono.empty<UpdateItemPriceDetailsDto>()
            `when`(service.updateItemPriceDetails(request)).thenReturn(serviceResponse)

            controller.updateItemPriceDetails(request).block()

            assert(false)
        } catch (e: Exception) {
            assertThat<Throwable>(e.cause, `is`<Throwable>(ItemNotFoundException(6)))
        }
    }
}