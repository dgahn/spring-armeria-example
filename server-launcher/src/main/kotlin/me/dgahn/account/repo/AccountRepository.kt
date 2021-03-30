package me.dgahn.account.repo

import me.dgahn.account.entity.Account
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class AccountRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(account: Account): String {
        em.persist(account)
        return account.email
    }

    fun find(email: String) = em.find(Account::class.java, email)
}
