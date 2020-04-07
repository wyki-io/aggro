package io.wyki.aggro.storage.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tag_value")
class TagValue : PanacheEntityUUID() {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "asset",
        nullable = false
    )
    var asset: Asset = Asset()

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "tag",
        nullable = false
    )
    var tag: Tag = Tag()

    @Column(nullable = false)
    var value: String = ""
}
