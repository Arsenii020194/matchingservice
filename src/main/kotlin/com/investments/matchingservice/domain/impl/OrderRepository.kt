package com.investments.matchingservice.domain.impl

import com.investments.matchingservice.domain.api.model.Order

interface OrderRepository {

    fun putOrder(order: Order)

    fun findOrderToMatch(order: Order): Order?

    fun delete(order: Order)
}