package io.wyki.aggro.storage.repositories

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.wyki.aggro.storage.entities.TagValue
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TagValueRepository : PanacheRepositoryBase<TagValue, UUID>
