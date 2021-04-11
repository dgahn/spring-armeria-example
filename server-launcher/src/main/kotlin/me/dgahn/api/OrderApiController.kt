package me.dgahn.api

import me.dgahn.entity.Address
import me.dgahn.entity.Order
import me.dgahn.entity.OrderItem
import me.dgahn.entity.OrderStatus
import me.dgahn.repo.OrderRepository
import me.dgahn.repo.OrderSearch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDateTime

class OrderApiController {

    @Autowired
    lateinit var orderRepo: OrderRepository

    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> {
        val all = orderRepo.findAll(OrderSearch())
        all.forEach {
            it.member?.name
            it.delivery.address
            val orderItems = it.orderItems
            orderItems.forEach { orderItem ->
                orderItem.item?.name
            }
        }
        return all
    }

    @GetMapping("/api/v2/orders")
    fun ordersV2() {
        val orders = orderRepo.findAll(OrderSearch())
        orders.map { OrderDto(it) }
    }

}

// Dto로 반환할 때는 엔티티를 넣으면 안된다. 엔티티에 대한 의존을 완전히 끊어야 한다.
data class OrderDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,
    val orderItems: List<OrderItemDto>
) {
    constructor(order: Order): this(
        orderId = order.id!!,
        name = order.member!!.name,
        orderDate = order.orderDate,
        orderStatus = order.status,
        address = order.member!!.address,
        orderItems = order.orderItems.map { OrderItemDto(it) }
    ) {
        order.orderItems.forEach { it.item!!.name }
    }
}

data class OrderItemDto(
    val itemName: String,
    val orderPrice: Int,
    val count: Int
) {
    constructor(orderItem: OrderItem) : this(
        itemName = orderItem.item!!.name,
        orderPrice = orderItem.orderPrice,
        count = orderItem.count
    )
}