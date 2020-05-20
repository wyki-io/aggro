package io.wyki.aggro.storage.entities

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "data_type")
class DataType() : PanacheEntityUUID() {
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

    @Column(nullable = false)
    var unit: String = ""

    constructor(
        name: String = "",
        description: String = "",
        unit: String = ""
    ) : this() {
        this.name = name
        this.description = description
        this.unit = unit
    }
}
