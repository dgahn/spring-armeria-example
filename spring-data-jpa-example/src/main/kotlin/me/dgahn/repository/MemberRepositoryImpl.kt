package me.dgahn.repository

import me.dgahn.entity.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class MemberRepositoryImpl(private val em: EntityManager) :
    MemberRepositoryCustom {

    override fun findMemberCustom(): List<Member> = em.createQuery(
        "SELECT m FROM Member m", Member::class.java
    ).resultList

}