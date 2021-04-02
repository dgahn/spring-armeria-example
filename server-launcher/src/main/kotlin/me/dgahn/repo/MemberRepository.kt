package me.dgahn.repo

import me.dgahn.entity.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class MemberRepository(
    @Autowired
    val em: EntityManager
) {

    fun save(member: Member) = em.persist(member)

    fun findOne(id: Long): Member {
        return em.find(Member::class.java, id)
    }

    fun findAll(): List<Member> = em.createQuery("SELECT m FROM Member m", Member::class.java)
        .resultList

    fun findByName(name: String) =
        em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
}