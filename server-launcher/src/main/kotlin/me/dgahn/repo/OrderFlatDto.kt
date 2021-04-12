package me.dgahn.repo

import me.dgahn.entity.Address
import me.dgahn.entity.OrderStatus
import java.time.LocalDateTime

data class OrderFlatDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,

    val itemName: String,
    val orderPrice: String,
    val count: Int
)
