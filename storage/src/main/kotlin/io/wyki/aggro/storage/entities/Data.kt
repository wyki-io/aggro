package io.wyki.aggro.storage.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import java.time.ZonedDateTime
import java.util.UUID
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "data")
class Data(
    value: Double = 0.0,
    timestamp: ZonedDateTime = ZonedDateTime.now(),
    asset: Asset = Asset(),
    dataType: DataType = DataType()
) : PanacheEntityUUID() {

    @Column(nullable = false)
    var value: Double = value

    @Column(nullable = false)
    var timestamp: ZonedDateTime = timestamp

    @ManyToOne(
        cascade = [PERSIST],
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "asset",
        nullable = false
    )
    var asset: Asset = asset

    @ManyToOne(
        cascade = [PERSIST],
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "data_type",
        nullable = false
    )
    var dataType: DataType = dataType

    companion object : PanacheCompanion<Data, UUID> {
        fun findByAssetAndTypeBucketPerHour(
            asset: Asset,
            type: DataType,
            start: ZonedDateTime,
            end: ZonedDateTime
        ): List<Data> = list(
            """
            select
            """.trimIndent(),
            asset,
            type,
            start,
            end
        )
    }
}
