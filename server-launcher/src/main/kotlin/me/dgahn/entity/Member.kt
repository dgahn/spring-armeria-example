package me.dgahn.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty

@Entity
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null,

    @NotEmpty
    var name: String,

    @Embedded
    var address: Address,

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf()
)
