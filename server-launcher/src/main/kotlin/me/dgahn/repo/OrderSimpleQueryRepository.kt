package me.dgahn.repo

import me.dgahn.entity.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderSimpleQueryRepository {

    @Autowired
    lateinit var em: EntityManager

    fun findOrderDtos(): List<OrderSimpleQueryDto> {
        return em.createQuery(
            "SELECT new me.dgahn.repo.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) FROM Order o" +
                    " JOIN FETCH o.member m" +
                    " JOIN FETCH o.delivery d", OrderSimpleQueryDto::class.java
        ).resultList
    }
}
