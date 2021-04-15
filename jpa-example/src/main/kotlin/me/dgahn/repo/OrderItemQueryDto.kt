package me.dgahn.repo

data class OrderItemQueryDto(
    val orderId: Long,
    val itemName: String,
    val orderPrice: String,
    val count: Int
)
