package io.wyki.aggro.storage.entities

import io.quarkus.hibernate.orm.panache.PanacheEntity
import java.util.UUID
import javax.persistence.Entity

@Entity
class AssetEntity : PanacheEntity() {
    var id: UUID = UUID.randomUUID()
    var name: String = ""
}
