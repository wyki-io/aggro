package io.wyki.aggro.storage.entities

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity(name ="asset")
class Asset : PanacheEntityUUID() {
    @Column(
        nullable = false,
        unique = true
    )
    var name: String = ""

    @OneToMany(
        cascade = [CascadeType.MERGE],
        targetEntity = TagValue::class,
        mappedBy = "asset",
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    var tags: Set<TagValue> = setOf()

    @OneToMany(
        cascade = [CascadeType.MERGE],
        targetEntity = DataType::class,
        fetch = FetchType.EAGER
    )
    @Fetch(value = FetchMode.SUBSELECT)
    var dataTypes: Set<DataType> = setOf()
}
