package io.wyki.aggro.storage.repositories

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.wyki.aggro.storage.entities.DataType
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

/**
 * Required due to PanacheEntity features not fully available for Kotlin
 */
@ApplicationScoped
class DataTypeRepository : PanacheRepositoryBase<DataType, UUID> {
    fun findByName(name: String): DataType? = find("name", name).firstResult()

    fun deleteByName(name: String) = delete("name", name)
}
