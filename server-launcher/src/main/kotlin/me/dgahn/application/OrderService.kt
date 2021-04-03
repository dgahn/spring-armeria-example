package me.dgahn.application

import me.dgahn.entity.Delivery
import me.dgahn.entity.Order
import me.dgahn.entity.OrderItem
import me.dgahn.repo.ItemRepository
import me.dgahn.repo.MemberRepository
import me.dgahn.repo.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {
        val member = memberRepository.findOne(memberId)
        val item = itemRepository.findOne(itemId)
        val delivery = Delivery(address = member.address)

        val orderItem = OrderItem.createOrderItem(item = item, orderPrice = item.price, count = count)

        val order = Order.createOrder(member = member, delivery = delivery, orderItem)

        orderRepository.save(order)

        return order.id!!
    }

    @Transactional
    fun cancelOrder(orderId: Long) {
        val order = orderRepository.findOne(orderId)
        order.cancel()
    }

//    fun findOrders(orderSeach: OrderSearch) {
//        return orderRepository.findOrderSearch()
//    }
}
