package com.investments.matchingservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.investments.matchingservice.domain.api.model.Order
import com.investments.matchingservice.domain.api.model.OrderStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import java.math.BigDecimal
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:29092", "port=29092"],
    topics = ["orders", "trade"]
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CreateEventTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var template: KafkaTemplate<String, Order>

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var consumer: KafkaConsumer

    @Test
    @Throws(Exception::class)
    fun givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() {
        val numberOfThreads = 10
        val service = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(numberOfThreads)
        for (i in 0 until numberOfThreads) {
            service.execute {
                template.send(
                    "orders", i.toLong().toString(), Order(
                        i.toLong(),
                        OrderStatus.CREATED,
                        64,
                        "test_id",
                        BigDecimal.valueOf(80),
                        i,
                        123,
                        123123123123
                    )
                )
                latch.countDown()
            }
        }
        latch.await(5, TimeUnit.SECONDS)
        consumer.latch.await(10, TimeUnit.SECONDS)
        assertEquals(5, consumer.tradeList.size)
    }

}
