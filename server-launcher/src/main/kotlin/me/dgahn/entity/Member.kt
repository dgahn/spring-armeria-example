package me.dgahn.entity

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long,
    val name: String,
    @Embedded
    val address: Address,

    @OneToMany(mappedBy = "member")
    val orders: List<Order> = emptyList()
)
