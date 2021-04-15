package me.dgahn.entity

import me.dgahn.entity.item.Item
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Category(
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    val id: Long,

    val name: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: List<Item> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Category? = null,

    @OneToMany(mappedBy = "parent")
    val child: List<Category> = emptyList()
)
