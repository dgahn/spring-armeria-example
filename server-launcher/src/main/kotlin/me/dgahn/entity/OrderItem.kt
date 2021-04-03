package me.dgahn.entity

import me.dgahn.entity.item.Item
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class OrderItem(
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null,

    var orderPrice: Int,
    var count: Int
) {
    fun cancel() {
        item?.addStock(count)
    }

    val totalPrice: Int
        get() = orderPrice * count

    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem(item = item, orderPrice = orderPrice, count = count)
            item.removeStock(count)
            return orderItem
        }
    }
}
