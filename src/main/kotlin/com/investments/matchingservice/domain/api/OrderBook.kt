package com.investments.matchingservice.domain.api

import com.investments.matchingservice.domain.api.model.Order

interface OrderBook {

    fun add(order: Order)

    fun findOrderToMatch(order: Order): Order?

    fun delete(order: Order)
}
//? delete order after matching
//split data structures (order book and order book repository)