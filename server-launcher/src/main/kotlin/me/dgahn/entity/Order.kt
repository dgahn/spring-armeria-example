package me.dgahn.entity

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    val id: Long? = null,

    // 지연 로딩 하는 경우 실제 값을 가져오지 않고 null로 채울 수 없기 때문에 ByteBuddyInterceptor()라는 프록시 객체를 생성한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery,

    val orderDate: LocalDateTime = LocalDateTime.now(),

    var status: OrderStatus = OrderStatus.ORDER
) {
    fun updateMember(member: Member) {
        this.member = member
        member.orders.add(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun updateDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    companion object {
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            val order = Order(member = member, delivery = delivery)
            orderItems.forEach { order.orderItems.add(it) }
            return order
        }
    }

    /**
     * 주문 취소
     */
    fun cancel() {
        if (delivery.status == DeliveryStatus.COMPLETED) {
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        this.status = OrderStatus.CANCEL
        orderItems.forEach { it.cancel() }
    }

    val totalPrice: Int
        get() = orderItems.sumBy { it.totalPrice }
}
