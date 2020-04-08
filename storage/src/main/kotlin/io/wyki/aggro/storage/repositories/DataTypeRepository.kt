package io.wyki.aggro.storage.repositories

import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.wyki.aggro.storage.entities.DataType
import javax.enterprise.context.ApplicationScoped

/**
 * Required due to PanacheEntity features not fully available for Kotlin
 */
@ApplicationScoped
class DataTypeRepository : PanacheRepository<DataType>
