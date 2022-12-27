package com.investments.matchingservice.domain.api.event

import java.math.BigDecimal

data class TradeCreateEvent(
    val count: Int,
    val instrumentId: String,
    val price: BigDecimal,
    val timestamp: String
) : Event {
    override val messageType: String = "TRADE"
}