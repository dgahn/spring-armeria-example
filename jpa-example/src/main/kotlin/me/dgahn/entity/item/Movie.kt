package me.dgahn.entity.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("M")
class Movie(
    val director: String,
    val actor: String,
    name: String,
    price: Int,
    stockQuantity: Int
) : Item(
    name = name,
    price = price,
    stockQuantity = stockQuantity
)
