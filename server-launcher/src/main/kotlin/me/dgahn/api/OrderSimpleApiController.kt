package me.dgahn.api

import me.dgahn.entity.Address
import me.dgahn.entity.Order
import me.dgahn.entity.OrderStatus
import me.dgahn.repo.OrderRepository
import me.dgahn.repo.OrderSearch
import me.dgahn.repo.OrderSimpleQueryDto
import me.dgahn.repo.OrderSimpleQueryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

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
    @Autowired
    lateinit var orderSimpleRepo: OrderSimpleQueryRepository

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): MutableList<Order> {
        val all = orderRepo.findAll(OrderSearch())
        all.forEach {
            it.member?.name
            it.delivery.address
        }
        return all
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<SimpleOrderDto> {
        return orderRepo.findAll(OrderSearch()).map { SimpleOrderDto(order = it) }
    }

    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): List<SimpleOrderDto> {
        return orderRepo.findAllWithMemberDelivery().map { SimpleOrderDto(order = it) }
    }

    @GetMapping("/api/v4/simple-orders")
    fun odersV4(): List<OrderSimpleQueryDto> {
        return orderSimpleRepo.findOrderDtos()
    }

    data class SimpleOrderDto(
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
}