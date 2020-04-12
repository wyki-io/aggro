package io.wyki.aggro.storage.repositories

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.wyki.aggro.storage.entities.Asset
import io.wyki.aggro.storage.entities.DataType
import io.wyki.aggro.storage.entities.Tag
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetRepository : PanacheRepositoryBase<Asset, UUID> {
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
