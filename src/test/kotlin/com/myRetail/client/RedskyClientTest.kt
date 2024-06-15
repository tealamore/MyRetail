package com.myRetail.client

import com.myRetail.config.RedskyConfig
import com.myRetail.model.Item
import com.myRetail.model.RedskyDetailsDto
import com.myRetail.model.Product
import com.myRetail.model.ProductDescription
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
class RedskyClientTest {
    @Mock
    lateinit var webClientBuilder: WebClient.Builder
    @Mock
    lateinit var redskyConfig: RedskyConfig

    @InjectMocks
    lateinit var redskyClient: RedskyClient

    @Mock
    lateinit var webClient: WebClient
    @Mock
    lateinit var webClientRequestHeadersUriSpec: WebClient.RequestHeadersUriSpec<*>
    @Mock
    lateinit var clientResponse: ClientResponse

    @Before
    fun before() {
        `when`(redskyConfig.url).thenReturn("testUrl/")
        `when`(webClientBuilder.baseUrl("testUrl/v2/pdp/tcin/6")).thenReturn(webClientBuilder)
        `when`(webClientBuilder.build()).thenReturn(webClient)
        `when`(webClient.get()).thenReturn(webClientRequestHeadersUriSpec)
    }

    @Test
    fun `returns data from call when successful`() {
        val itemDetailsDto = RedskyDetailsDto(Product(Item(ProductDescription("testName"))))
        `when`(webClientRequestHeadersUriSpec.exchange()).thenReturn(Mono.just(clientResponse))
        `when`(clientResponse.toEntity(RedskyDetailsDto::class.java)).thenReturn(Mono.just(ResponseEntity<RedskyDetailsDto>(itemDetailsDto, HttpStatus.OK)))

        val itemName = redskyClient.getProductName(6).block()

        assertThat<String>(itemName, `is`<String>("testName"))
    }

    @Test
    fun `returns mono of null when call fails`() {
        `when`(webClientRequestHeadersUriSpec.exchange()).thenReturn(Mono.just(clientResponse))
        `when`(clientResponse.toEntity(RedskyDetailsDto::class.java)).thenReturn(Mono.just(ResponseEntity<RedskyDetailsDto>(HttpStatus.EXPECTATION_FAILED)))

        val itemName = redskyClient.getProductName(6).block()

        assertThat(itemName, nullValue())
    }
}