package me.dgahn.repo

import me.dgahn.entity.Address
import me.dgahn.entity.Order
import me.dgahn.entity.OrderStatus
import java.time.LocalDateTime

data class OrderSimpleQueryDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,
) {
    constructor(order: Order) : this(
        orderId = order.id!!,
        name = order.member!!.name,
        orderDate = order.orderDate,
        orderStatus = order.status,
        address = order.delivery.address
    )
}
