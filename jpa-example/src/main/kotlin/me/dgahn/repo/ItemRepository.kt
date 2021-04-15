package me.dgahn.repo

import me.dgahn.entity.item.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class ItemRepository {

    @Autowired
    lateinit var em: EntityManager

    fun save(item: Item) {
        if (item.id == null) {
            em.persist(item)
        } else {
            em.merge(item)
        }
    }

    fun findOne(id: Long): Item = em.find(Item::class.java, id)

    fun findAll(): List<Item> =
        em.createQuery("SELECT i FROM Item i", Item::class.java)
            .resultList
}
