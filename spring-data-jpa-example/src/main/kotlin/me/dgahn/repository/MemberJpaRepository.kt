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

    fun delete(member: Member) = em.remove(member)

    fun findAll(): List<Member> {
        val result = em.createQuery("SELECT m FROM Member m", Member::class.java).resultList
        return result
    }

    fun findById(id: Long): Member? = em.find(Member::class.java, id)

    fun count(): Long =
        em.createQuery("SELECT count(m) FROM Member m", java.lang.Long::class.java).singleResult.toLong()

    fun find(id: Long): Member = em.find(Member::class.java, id)

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int) =
        em.createQuery("SELECT m FROM Member m WHERE m.username = :username AND m.age > :age")
            .setParameter("username", username)
            .setParameter("age", age)
            .resultList
}