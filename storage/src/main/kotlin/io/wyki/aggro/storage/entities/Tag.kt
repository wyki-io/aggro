package io.wyki.aggro.storage.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity(name = "tag")
class Tag(
    @Column(
        nullable = false,
        unique = true
    )
    var name: String = "",
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    var description: String = "",
    @OneToMany(
        mappedBy = "tag",
        orphanRemoval = true
    )
    var values: MutableSet<TagValue> = mutableSetOf()
) : PanacheEntityUUID() {

    companion object : PanacheCompanion<Tag, UUID> {
        fun findByName(name: String): Tag? = find("name", name).firstResult()

        fun deleteByName(name: String) = delete("name", name)
    }
}
