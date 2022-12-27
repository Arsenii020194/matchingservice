package com.investments.matchingservice.domain.api.event

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.investments.matchingservice.domain.api.model.Order

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "messageType"
)
@JsonSubTypes(
    JsonSubTypes.Type(
        value = TradeCreateEvent::class,
        name = "TRADE"
    ),
    JsonSubTypes.Type(
        value = Order::class,
        name = "ORDER"
    )
)
interface Event{
    val messageType: String
}