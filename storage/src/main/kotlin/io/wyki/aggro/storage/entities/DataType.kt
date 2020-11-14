package io.wyki.aggro.storage.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "data_type")
class DataType(
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
    @Column(nullable = false)
    var unit: String = ""
) : PanacheEntityUUID() {

    companion object : PanacheCompanion<DataType, UUID> {
        fun findByName(name: String): DataType? = find("name", name).firstResult()

        fun deleteByName(name: String) = delete("name", name)
    }
}
