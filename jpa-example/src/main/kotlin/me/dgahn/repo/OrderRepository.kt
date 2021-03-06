package me.dgahn.repo

import com.querydsl.jpa.impl.JPAQueryFactory
import me.dgahn.entity.Order
import me.dgahn.entity.QMember.member
import me.dgahn.entity.QOrder.order
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderRepository(
    private val em: EntityManager
) {
    val query: JPAQueryFactory = JPAQueryFactory(em)

    fun save(order: Order) = em.persist(order)

    fun findOne(id: Long) = em.find(Order::class.java, id)

    fun findAll(orderSearch: OrderSearch): List<Order> {
        return query.select(order)
            .from(order)
            .join(order.member, member)
            .limit(1000)
            .where(
                if (orderSearch.orderStatus != null) order.status.eq(orderSearch.orderStatus) else null,
                if (orderSearch.memberName != null) member.name.like(orderSearch.memberName) else null
            )
            .fetch()
    }

    // JPA에만 있는 문법으로 fetch를 하면 실제 데이터를 다 가져와서 Entity의 값을 채워준다.
    fun findAllWithMemberDelivery(): List<Order> {
        return em.createQuery(
            "SELECT o FROM Order o" +
                " JOIN FETCH o.member m" +
                " JOIN FETCH o.delivery d",
            Order::class.java
        ).resultList
    }

    // DB쿼리에서는 DISTINCT를 하지 못한다. 모든 col이 같지 않기 때문이다. JPA에서는 Order 객체가 중복되는 경우 중복을 제거해준다.
    // 1 대 다 관계에서 FETCH 쿼리를 하는데 페이징을 하면 쿼리에서 동작을 하지 않는다. DB에서 처리하지 않고 메모리에서 처리하기 때문에 모든 데이터를 메모리로 가져와서 처리하기 때문이다.
    // 컬렉션 FETCH JOIN 한개만 하자.
    fun findAllWithItem(): List<Order> = em.createQuery(
        "SELECT DISTINCT o FROM Order o" +
            " JOIN FETCH o.member m" +
            " JOIN FETCH o.delivery d" +
            " JOIN FETCH o.orderItems oi" +
            " JOIN FETCH oi.item i",
        Order::class.java
    ).resultList

    fun findAllWithMemberDelivery(offset: Int, limit: Int): List<Order> {
        return em.createQuery(
            "SELECT o FROM Order o" +
                " JOIN FETCH o.member m" +
                " JOIN FETCH o.delivery d",
            Order::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
