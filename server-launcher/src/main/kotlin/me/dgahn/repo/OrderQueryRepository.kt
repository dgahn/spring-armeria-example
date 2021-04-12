package me.dgahn.repo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderQueryRepository {

    @Autowired
    lateinit var em: EntityManager

    fun findOrderQueryDtos(): List<OrderQueryDto> {
        val result = findOrders()
        return result.map {
            val orderItems = findOrderItems(it.orderId)
            it.copy(orderItems = orderItems)
        }
    }

    private fun findOrderItems(orderId: Long): List<OrderItemQueryDto> {
        return em.createQuery(
            "SELECT me.dgahn.repo.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                    " FROM OrderItem oi" +
                    " JOIN oi.item i" +
                    " WHERE oi.order.id = : orderId", OrderItemQueryDto::class.java
        )
            .setParameter("orderId", orderId)
            .resultList
    }

    fun findOrders(): List<OrderQueryDto> {
        return em.createQuery(
            "SELECT me.dgahn.repo.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
                    + " FROM Order o"
                    + " JOIN o.member m"
                    + " JOIN o.delivery d", OrderQueryDto::class.java
        ).resultList
    }

    fun findOrderQueryDtosOptimization(): List<OrderQueryDto> {
        val result = findOrders()
        val orderIds = result.map { it.orderId }
        val orderItems = em.createQuery(
            "SELECT me.dgahn.repo.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                    " FROM OrderItem oi" +
                    " JOIN oi.item i" +
                    " WHERE oi.order.id IN : orderIds", OrderItemQueryDto::class.java
        ).setParameter("orderIds", orderIds).resultList

        val orderItemMap = orderItems.groupBy { it.orderId }

        return result.map { it.copy(orderItems = orderItemMap.getValue(it.orderId)) }
    }

}