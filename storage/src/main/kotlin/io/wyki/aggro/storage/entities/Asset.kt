package io.wyki.aggro.storage.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.util.UUID
import javax.persistence.CascadeType.ALL
import javax.persistence.CascadeType.DETACH
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity(name = "asset")
class Asset : PanacheEntityUUID() {
    @Column(
        nullable = false,
        unique = true
    )
    var name: String = ""

    @OneToMany(
        mappedBy = "asset",
        cascade = [ALL],
        orphanRemoval = true
    )
    var tags: MutableSet<TagValue> = mutableSetOf()

    @ManyToMany(cascade = [PERSIST, DETACH, MERGE])
    @JoinTable(
        name = "join__asset__data_type",
        joinColumns = [ JoinColumn(name = "asset") ],
        inverseJoinColumns = [ JoinColumn(name = "data_type") ]
    )
    var dataTypes: MutableSet<DataType> = mutableSetOf()

    companion object: PanacheCompanion<Asset, UUID> {
        fun findByName(name: String): Asset? = find("name", name).firstResult()

        fun deleteByName(name: String) = delete("name", name)

        fun findByDataType(dataType: DataType): List<Asset> = list(
            """
            select a
            from asset as a
                left join a.dataTypes as dt
            where dt.id = ?1
            """.trimIndent(),
            dataType.id
        )

        fun findByDataTypeName(dataTypeName: String): List<Asset> = list(
            """
            select a
            from asset as a
                left join a.dataTypes as dt
            where dt.name = ?1
            """.trimIndent(),
            dataTypeName
        )

        fun findByTag(tag: Tag): List<Asset> = list(
            """
            select a
            from asset as a
                left join a.tags as tv
                left join tv.tag as tag
            where tag.id = ?1
            """.trimIndent(),
            tag.id
        )

        fun findByTagName(tagName: String): List<Asset> = list(
            """
            select a
            from asset as a
                left join a.tags as tv
                left join tv.tag as tag
            where tag.name = ?1
            """.trimIndent(),
            tagName
        )

        fun findByTagAndValue(tag: Tag, value: String): List<Asset> = list(
            """
            select a
            from asset as a
                left join a.tags as tv
                left join tv.tag as tag
            where tag.id = ?1
            and tv.value = ?2
            """.trimIndent(),
            tag.id,
            value
        )

        fun findByTagNameAndValue(tagName: String, value: String): List<Asset> = list(
            """
            select a
            from asset as a
                left join a.tags as tv
                left join tv.tag as tag
            where tag.name = ?1
            and tv.value = ?2
            """.trimIndent(),
            tagName,
            value
        )
    }
}
