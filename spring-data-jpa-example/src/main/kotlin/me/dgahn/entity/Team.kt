package me.dgahn.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Team(
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    val id: Long? = null,
    var name: String,

    @OneToMany(mappedBy = "team")
    val members: MutableList<Member> = mutableListOf()
) {
    override fun toString(): String {
        return "Team(id=$id, name='$name')"
    }
}




