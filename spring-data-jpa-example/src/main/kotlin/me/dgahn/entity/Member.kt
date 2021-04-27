package me.dgahn.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedQuery

@Entity
@NamedQuery(
    name = "Member.findByUsername",
    query = "SELECT m FROM Member m WHERE m.username = :username"
)
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null,
    var username: String? = null,
    var age: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null
): BaseEntity() {
    constructor(username: String, age: Int, team: Team) : this(
        username = username,
        age = age
    ) {
        changeTeam(team)
    }

    final fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }

    override fun toString(): String {
        return "Member(id=$id, username='$username', age=$age)"
    }
}