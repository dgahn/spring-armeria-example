package me.dgahn.entity

import javax.persistence.Embeddable

@Embeddable
data class Address(
    var city: String,
    var street: String,
    var zipCode: String
)
