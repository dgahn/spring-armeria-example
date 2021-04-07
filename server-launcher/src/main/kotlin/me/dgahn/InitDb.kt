package me.dgahn

import me.dgahn.entity.Address
import me.dgahn.entity.Delivery
import me.dgahn.entity.Member
import me.dgahn.entity.Order
import me.dgahn.entity.OrderItem
import me.dgahn.entity.item.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class InitDb {

    @Autowired
    lateinit var initService: InitService

    @PostConstruct
    fun init() {
        initService.dbInit1()
        initService.dbInit2()
    }

    companion object {
        @Component
        @Transactional
        class InitService {
            @Autowired
            lateinit var em: EntityManager

            fun dbInit1() {
                val member = Member(
                    name = "userA",
                    address = Address(city = "서울", street = "1", zipCode = "1111")
                )
                em.persist(member)

                val book1 = Book(
                    name = "JPA1 BOOK",
                    price = 10_000,
                    stockQuantity = 100,
                    author = "author",
                    isbn = "isbn"
                )
                em.persist(book1)

                val book2 = Book(
                    name = "JPA2 BOOK",
                    price = 20_000,
                    stockQuantity = 100,
                    author = "author",
                    isbn = "isbn"
                )
                em.persist(book2)

                val orderItem1 = OrderItem.createOrderItem(book1, 10_000, 1)
                val orderItem2 = OrderItem.createOrderItem(book2, 20_000, 2)

                val delivery = Delivery(
                    address = member.address
                )
                val order = Order.createOrder(member, delivery, orderItem1, orderItem2)
                em.persist(order)
            }

            fun dbInit2() {
                val member = Member(
                    name = "userB",
                    address = Address(city = "서울", street = "1", zipCode = "1111")
                )
                em.persist(member)

                val book1 = Book(
                    name = "Spring BOOK",
                    price = 10_000,
                    stockQuantity = 100,
                    author = "author",
                    isbn = "isbn"
                )
                em.persist(book1)

                val book2 = Book(
                    name = "Spring2 BOOK",
                    price = 20_000,
                    stockQuantity = 100,
                    author = "author",
                    isbn = "isbn"
                )
                em.persist(book2)

                val orderItem1 = OrderItem.createOrderItem(book1, 10_000, 1)
                val orderItem2 = OrderItem.createOrderItem(book2, 20_000, 2)

                val delivery = Delivery(
                    address = member.address
                )
                val order = Order.createOrder(member, delivery, orderItem1, orderItem2)
                em.persist(order)
            }
        }
    }
}
