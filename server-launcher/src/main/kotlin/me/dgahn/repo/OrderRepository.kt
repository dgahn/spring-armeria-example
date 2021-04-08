package me.dgahn.repo

import me.dgahn.entity.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderRepository {

    @Autowired
    lateinit var em: EntityManager

    fun save(order: Order) = em.persist(order)

    fun findOne(id: Long) = em.find(Order::class.java, id)

    fun findAll(orderSearch: OrderSearch): MutableList<Order> {
        val queryString = "SELECT o FROM Order o JOIN o.member m WHERE o.status = :status AND m.name LIKE :name"
        return em.createQuery(queryString, Order::class.java)
            .setParameter("status", orderSearch.orderStatus)
            .setParameter("name", orderSearch.memberName)
            .setMaxResults(1000)
            .resultList
    }
}
