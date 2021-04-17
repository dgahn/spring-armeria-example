package me.dgahn.repository

import me.dgahn.entity.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class MemberJpaRepository(
    val em: EntityManager
    ) {

    fun save(member: Member): Member {
        em.persist(member);
        return member
    }

    fun find(id: Long) = em.find(Member::class.java, id)
}