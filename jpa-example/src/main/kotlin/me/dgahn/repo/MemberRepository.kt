package me.dgahn.repo

import me.dgahn.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByName(name: String): List<Member>
}
