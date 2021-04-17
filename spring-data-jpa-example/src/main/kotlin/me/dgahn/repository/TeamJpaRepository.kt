package me.dgahn.repository

import me.dgahn.entity.Team
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class TeamJpaRepository(
    val em: EntityManager
) {

    fun save(team: Team): Team {
        em.persist(team)
        return team
    }

    fun delete(team: Team) = em.remove(team)

    fun findAll(): List<Team> = em.createQuery("SELECT t FROM Team t", Team::class.java).resultList

    fun findById(id: Long): Team? = em.find(Team::class.java, id)

    fun count(): Long =
        em.createQuery("SELECT count(t) FROM Team t", java.lang.Long::class.java).singleResult.toLong()

}