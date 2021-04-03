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
}
