package me.dgahn.application

import me.dgahn.entity.item.Item
import me.dgahn.repo.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService {

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    fun findItems() = itemRepository.findAll()

    fun findOne(itemId: Long) = itemRepository.findOne(itemId)
}
