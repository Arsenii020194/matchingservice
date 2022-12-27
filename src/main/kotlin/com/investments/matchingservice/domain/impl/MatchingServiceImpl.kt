package com.investments.matchingservice.domain.impl

import com.investments.matchingservice.domain.api.MatchingService
import com.investments.matchingservice.domain.api.event.TradeCreateEvent
import com.investments.matchingservice.domain.api.model.Order
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MatchingServiceImpl(private val orderRepository: OrderRepository) : MatchingService {
    override fun tryMatch(order: Order): TradeCreateEvent? {
        orderRepository.findOrderToMatch(order)?.let {
            orderRepository.delete(it)
            return TradeCreateEvent(
                order.count,
                order.instrumentId,
                order.price,
                LocalDateTime.now().toString()
            )
        }
        return null
    }
}