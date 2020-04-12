package io.wyki.aggro.storage.entities

import javax.persistence.CascadeType.ALL
import javax.persistence.CascadeType.DETACH
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity(name = "asset")
class Asset : PanacheEntityUUID() {
    @Column(
        nullable = false,
        unique = true
    )
    var name: String = ""

    @OneToMany(
        mappedBy = "asset",
        cascade = [ALL],
        orphanRemoval = true
    )
    var tags: MutableSet<TagValue> = mutableSetOf()

    @ManyToMany(cascade = [PERSIST, DETACH, MERGE])
    @JoinTable(
        name = "join__asset__data_type",
        joinColumns = [ JoinColumn(name = "asset") ],
        inverseJoinColumns = [ JoinColumn(name = "data_type") ]
    )
    var dataTypes: MutableSet<DataType> = mutableSetOf()
}
