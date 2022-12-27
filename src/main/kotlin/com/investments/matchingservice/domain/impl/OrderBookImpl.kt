package com.investments.matchingservice.domain.impl

import com.investments.matchingservice.domain.api.OrderBook
import com.investments.matchingservice.domain.api.model.Order
import com.investments.matchingservice.domain.api.model.OrderStatus
import org.springframework.stereotype.Component
import java.util.concurrent.locks.ReentrantReadWriteLock

@Component
class OrderBookImpl(
    private val book: MutableList<Order> = mutableListOf(),
    private val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()
) : OrderBook {

    constructor(order: Order) : this() {
        book.add(order)
    }

    override fun add(order: Order) {
        val writeLock = lock.writeLock()
        writeLock.lock()
        try {
            book.add(order)
        } finally {
            writeLock.unlock()
        }
    }

    override fun findOrderToMatch(order: Order): Order? {
        val readLock = lock.readLock()
        readLock.lock()
        try {
            return book.find {
                it.orderStatus == OrderStatus.CREATED
                        && it.count == order.count
                        && it.type != order.type
            }
        } finally {
            readLock.unlock()
        }
    }

    override fun delete(order: Order) {
        val writeLock = lock.writeLock()
        writeLock.lock()
        try {
            book.removeIf { it.orderId == order.orderId }
        } finally {
            writeLock.unlock()
        }
    }
}