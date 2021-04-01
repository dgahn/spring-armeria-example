package me.dgahn.entity

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Delivery(
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long,

    @Embedded
    val address: Address,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    val order: Order
)
