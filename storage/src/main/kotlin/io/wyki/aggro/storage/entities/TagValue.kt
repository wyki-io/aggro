package io.wyki.aggro.storage.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import java.util.UUID
import javax.persistence.CascadeType.ALL
import javax.persistence.CascadeType.DETACH
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tag_value")
class TagValue(
    @Column(nullable = false)
    var value: String = "",
    asset: Asset = Asset(),
    tag: Tag = Tag()
) : PanacheEntityUUID() {


    @ManyToOne(
        fetch = LAZY,
        cascade = [DETACH, MERGE, PERSIST, REFRESH],
        optional = false
    )
    @JoinColumn(name = "asset")
    var asset: Asset = asset
        set(asset) {
            field = asset
            asset.tags.add(this)
        }

    @ManyToOne(
        fetch = LAZY,
        cascade = [DETACH, MERGE, PERSIST, REFRESH],
        optional = false
    )
    @JoinColumn(name = "tag")
    var tag: Tag = tag
        set(tag) {
            field = tag
            tag.values.add(this)
        }

    /*
    Init block required to trigger setter, therefore updating One side of
    relation
     */
    init {
        this.asset = asset
        this.tag = tag
    }

    companion object: PanacheCompanion<TagValue, UUID>
}
