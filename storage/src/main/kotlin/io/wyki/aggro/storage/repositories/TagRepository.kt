package io.wyki.aggro.storage.repositories

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.wyki.aggro.storage.entities.Tag
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TagRepository : PanacheRepositoryBase<Tag, UUID> {
    fun findByName(name: String): Tag? = find("name", name).firstResult()

    fun deleteByName(name: String) = delete("name", name)
}
