package me.dgahn.entity.item

import me.dgahn.entity.Category
import me.dgahn.exception.NotEnoughStockException
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToMany

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    val id: Long? = null,

    val name: String,
    val price: Int,
    var stockQuantity: Int,

    @ManyToMany
    val categories: List<Category> = emptyList()

) {
    fun addStock(quantity: Int) {
        this.stockQuantity += quantity
    }

    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        this.stockQuantity -= quantity
    }
}
