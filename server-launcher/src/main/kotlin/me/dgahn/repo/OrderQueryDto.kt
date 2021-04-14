package me.dgahn.repo

import com.fasterxml.jackson.annotation.JsonIgnore
import me.dgahn.entity.Address
import me.dgahn.entity.OrderStatus
import java.time.LocalDateTime

data class OrderQueryDto(
    @JsonIgnore
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,
    val orderItems: List<OrderItemQueryDto>? = null
)
