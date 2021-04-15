package me.dgahn.repo

import me.dgahn.entity.OrderStatus

class OrderSearch(
    val memberName: String? = null,
    val orderStatus: OrderStatus? = null
)
