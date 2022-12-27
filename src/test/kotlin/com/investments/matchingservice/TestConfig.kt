package com.investments.matchingservice

import com.investments.matchingservice.domain.api.event.Event
import com.investments.matchingservice.domain.api.event.TradeCreateEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import java.util.concurrent.CountDownLatch


@Configuration
@Profile("test")
class TestSecurityConfig {
    @Bean
    fun kafkaConsumer(): KafkaConsumer {
        return KafkaConsumer()
    }
}

class KafkaConsumer {
    var latch = CountDownLatch(5)
    var tradeList: MutableList<TradeCreateEvent?> = mutableListOf()

    @KafkaListener(
        topics = ["trade"],
        groupId = "trade-consumers"
    )
    fun receive(trade: Event?) {
        tradeList.add(trade as TradeCreateEvent?)
        latch.countDown()
    }
}