package me.dgahn.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import me.dgahn.entity.Address
import me.dgahn.entity.Member
import me.dgahn.entity.OrderStatus
import me.dgahn.entity.item.Book
import me.dgahn.exception.NotEnoughStockException
import me.dgahn.repo.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
@SpringBootTest
@Transactional
open class OrderServiceTest : AnnotationSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    fun orderTest() {
        val member = createMember()
        em.persist(member)

        val stockQuantity = 10
        val book = createBook(stockQuantity, "시골 JPA", 10_000, "저자")
        em.persist(book)

        val orderCount = 2
        val orderId = orderService.order(memberId = member.id!!, itemId = book.id!!, count = orderCount)

        val findOrder = orderRepository.findOne(orderId)

        findOrder.status shouldBe OrderStatus.ORDER
        findOrder.orderItems.size shouldBe 1
        findOrder.totalPrice shouldBe orderCount * book.price
        book.stockQuantity shouldBe stockQuantity - orderCount
    }

    @Test
    fun overOrderTest() {
        val stockQuantity = 1
        val member = createMember()
        val item = createBook(stockQuantity, "시골 JPA1", 12_000, "저자1")

        val orderCount = 11

        shouldThrow<NotEnoughStockException> {
            orderService.order(member.id!!, item.id!!, orderCount)
        }
    }

    @Test
    fun cancelTest() {
        val member = createMember()
        val stockQuantity = 10
        val item = createBook(stockQuantity, "시골 JPA1", 10_000, "저자2")

        val orderCount = 2
        val orderId = orderService.order(member.id!!, item.id!!, orderCount)

        orderService.cancelOrder(orderId)

        val findOrder = orderRepository.findOne(orderId)

        findOrder.status shouldBe OrderStatus.CANCEL
        item.stockQuantity shouldBe stockQuantity
    }

    private fun createBook(stockQuantity: Int, name: String, price: Int, author: String): Book {
        val book = Book(name = name, price = price, stockQuantity = stockQuantity, author = author, isbn = "123")
        em.persist(book)
        return book
    }

    private fun createMember(): Member {
        val member = Member(
            name = "회원1",
            address = Address(city = "서울", street = "강가", zipCode = "123-123")
        )
        em.persist(member)
        return member
    }
}
