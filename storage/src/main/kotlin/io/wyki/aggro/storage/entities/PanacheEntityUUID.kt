package io.wyki.aggro.storage.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import java.util.UUID
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class PanacheEntityUUID : PanacheEntityBase {
    @Id
    @GeneratedValue
    lateinit var id: UUID
}
