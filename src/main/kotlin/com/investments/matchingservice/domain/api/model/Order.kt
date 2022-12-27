package com.investments.matchingservice.domain.api.model

import com.investments.matchingservice.domain.api.event.Event
import java.math.BigDecimal

data class Order(
    val orderId: Long?,
    var orderStatus: OrderStatus?,
    val count: Int,
    val instrumentId: String,
    val price: BigDecimal,
    val type: Int,
    val userAccountId: Long,
    val userId: Long
) : Event {
    override val messageType: String = "ORDER"
}