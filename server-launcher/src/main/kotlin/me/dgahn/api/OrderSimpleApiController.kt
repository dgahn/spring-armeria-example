package me.dgahn.api

import me.dgahn.entity.Order
import me.dgahn.repo.OrderRepository
import me.dgahn.repo.OrderSearch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * xToOne (ManyToOne, OneToOne)최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 * 양반향 관계에 있을 경우에는 JsonIgnore을 해야한다.
 */
@RestController
class OrderSimpleApiController {

    @Autowired
    lateinit var orderRepo: OrderRepository

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): MutableList<Order> {
        val all =  orderRepo.findAll(OrderSearch())
        all.forEach {
            it.member?.name
            it.delivery.address
        }
        return all
    }
}