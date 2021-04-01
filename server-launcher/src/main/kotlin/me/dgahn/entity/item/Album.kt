package me.dgahn.entity.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album(
    val artist: String,
    val etc: String,
    name: String,
    price: Int,
    stockQuantity: Int
) : Item(
    name = name,
    price = price,
    stockQuantity = stockQuantity
)
