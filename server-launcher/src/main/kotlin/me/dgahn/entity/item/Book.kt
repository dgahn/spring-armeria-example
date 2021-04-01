package me.dgahn.entity.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
class Book(
    val author: String,
    val isbn: String,
    name: String,
    price: Int,
    stockQuantity: Int
) : Item(
    name = name,
    price = price,
    stockQuantity = stockQuantity
)
