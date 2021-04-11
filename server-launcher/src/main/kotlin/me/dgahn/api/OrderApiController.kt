package me.dgahn.api

import me.dgahn.entity.Order
import me.dgahn.repo.OrderRepository
import me.dgahn.repo.OrderSearch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping

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

}