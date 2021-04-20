package me.dgahn.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PreUpdate

@MappedSuperclass
class JpaBaseEntity(
    @Column(updatable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var updatedDate: LocalDateTime = LocalDateTime.now()
) {

    @PreUpdate
    fun preUpdate() {
        updatedDate = LocalDateTime.now()
    }
}