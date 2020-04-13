package io.wyki.aggro.storage.entities

import java.time.ZonedDateTime
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "data")
class Data : PanacheEntityUUID() {
    @Column(nullable = false)
    var timestamp: ZonedDateTime = ZonedDateTime.now()

    @ManyToOne(
        cascade = [PERSIST],
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "asset",
        nullable = false
    )
    var asset: Asset = Asset()

    @ManyToOne(
        cascade = [PERSIST],
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "data_type",
        nullable = false
    )
    var dataType: DataType = DataType()

    @Column(nullable = false)
    var value: Double = 0.0
}
