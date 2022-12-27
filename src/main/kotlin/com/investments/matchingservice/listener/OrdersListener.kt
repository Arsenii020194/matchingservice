package com.investments.matchingservice.listener

import com.investments.matchingservice.domain.api.MatchingService
import com.investments.matchingservice.domain.api.event.Event
import com.investments.matchingservice.domain.api.event.TradeCreateEvent
import com.investments.matchingservice.domain.api.model.Order
import com.investments.matchingservice.domain.impl.OrderRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class OrdersListener(
    private val orderRepository: OrderRepository,
    private val matchingService: MatchingService,
    private val kafkaTemplate: KafkaTemplate<String, TradeCreateEvent>
) {
    @KafkaListener(
        topics = ["orders"],
        groupId = "order-consumers"
    )
    fun listenGroupLongMessage(message: Event?) {
        (message as? Order)?.let {
            val trade = matchingService.tryMatch(message)
            if (trade != null) {
                kafkaTemplate.send("trade", trade.timestamp, trade)
            } else {
                orderRepository.putOrder(message)
            }
        }
    }
}