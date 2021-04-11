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

    fun findAll(orderSearch: OrderSearch): List<Order> {
        val queryString = "SELECT o FROM Order o JOIN o.member m WHERE o.status = :status AND m.name LIKE :name"
        return em.createQuery(queryString, Order::class.java)
            .setParameter("status", orderSearch.orderStatus)
            .setParameter("name", orderSearch.memberName)
            .setMaxResults(1000)
            .resultList
    }

    // JPA에만 있는 문법으로 fetch를 하면 실제 데이터를 다 가져와서 Entity의 값을 채워준다.
    fun findAllWithMemberDelivery(): List<Order> {
        return em.createQuery(
            "SELECT o FROM Order o" +
                    " JOIN FETCH o.member m" +
                    " JOIN FETCH o.delivery d", Order::class.java
        ).resultList
    }

}
