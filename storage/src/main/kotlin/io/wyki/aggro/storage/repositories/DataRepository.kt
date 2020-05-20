package io.wyki.aggro.storage.repositories

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.wyki.aggro.storage.entities.Asset
import io.wyki.aggro.storage.entities.Data
import io.wyki.aggro.storage.entities.DataType
import java.time.ZonedDateTime
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataRepository : PanacheRepositoryBase<Data, UUID> {
    fun findByAssetAndTypeBucketPerHour(
        asset: Asset,
        type: DataType,
        start: ZonedDateTime,
        end: ZonedDateTime
    ) : List<Data> = list(
        """
            select
        """.trimIndent(),
        asset,
        type,
        start,
        end
    )
}
