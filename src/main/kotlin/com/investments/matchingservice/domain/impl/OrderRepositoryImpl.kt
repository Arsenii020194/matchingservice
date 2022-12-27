package com.investments.matchingservice.domain.impl

import com.investments.matchingservice.domain.api.OrderBook
import com.investments.matchingservice.domain.api.model.Order
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class OrderRepositoryImpl(private val orderBookMap: MutableMap<String, OrderBook> = ConcurrentHashMap()) :
    OrderRepository {

    override fun putOrder(order: Order) {
        if (orderBookMap.containsKey(order.instrumentId)) {
            orderBookMap[order.instrumentId]!!.add(order)
        } else {
            orderBookMap[order.instrumentId] = OrderBookImpl(order)
        }
    }

    override fun findOrderToMatch(order: Order): Order? {
        return orderBookMap[order.instrumentId]?.findOrderToMatch(order)
    }

    override fun delete(order: Order) {
        orderBookMap[order.instrumentId]?.delete(order)
    }
}