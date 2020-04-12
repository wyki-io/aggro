package io.wyki.aggro.storage.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity(name = "tag")
class Tag : PanacheEntityUUID() {
    @Column(
        nullable = false,
        unique = true
    )
    var name: String = ""

    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    var description: String = ""

    @OneToMany(
        mappedBy = "tag",
        // cascade = [ALL],
        orphanRemoval = true
    )
    var values: MutableSet<TagValue> = mutableSetOf()
}
