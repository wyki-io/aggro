package io.wyki.aggro.storage.entities

object SampleEntities {
    const val sampleAssetName = "asset name"
    const val sampleDataTypeName = "dataType name"
    const val sampleTagName = "tag name"
    const val sampleTagValueName = "tag value"

    fun sampleDataType(name: String = sampleDataTypeName): DataType {
        val ret = DataType()
        ret.name = name
        return ret
    }

    fun sampleTag(name: String = sampleTagName): Tag {
        val ret = Tag()
        ret.name = name
        return ret
    }

    fun sampleTagValue(value: String = sampleTagValueName, asset: Asset, tag: Tag): TagValue {
        return TagValue(value, asset, tag)
    }

    fun sampleAsset(name: String = sampleAssetName): Asset {
        val ret = Asset()
        ret.name = name
        return ret
    }
}
