package com.investments.matchingservice.domain.api

import com.investments.matchingservice.domain.api.event.TradeCreateEvent
import com.investments.matchingservice.domain.api.model.Order

interface MatchingService {

    fun tryMatch(order: Order): TradeCreateEvent?
}