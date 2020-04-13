package io.wyki.aggro.storage.repositories

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.wyki.aggro.storage.entities.Data
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataRepository : PanacheRepositoryBase<Data, UUID>
