package io.wyki.aggro.storage.entities

import javax.persistence.CascadeType.PERSIST
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tag_value")
class TagValue() : PanacheEntityUUID() {

    constructor(value: String, asset: Asset, tag: Tag) : this() {
        this.value = value
        this.asset = asset
        this.tag = tag
    }

    @ManyToOne(
        fetch = LAZY,
        cascade = [PERSIST],
        optional = false
    )
    @JoinColumn(name = "asset")
    var asset: Asset = Asset()
        set(value) {
            field = value
            value.tags.add(this)
        }

    @ManyToOne(
        fetch = LAZY,
        cascade = [PERSIST],
        optional = false
    )
    @JoinColumn(name = "tag")
    var tag: Tag = Tag()
        set(value) {
            field = value
            value.values.add(this)
        }

    @Column(nullable = false)
    var value: String = ""
}
