package io.wyki.aggro.storage.entities

import java.time.ZonedDateTime

object SampleEntities {
    val sampleDataTypeName = "dataType name"
    val sampleDataTypeDescription = "dataType description"
    val sampleDataTypeUnit = "dataType unit"

    val sampleAssetName = "asset name"

    val sampleTagName = "tag name"
    val sampleTagDescription = "tag description"

    val sampleTagValueName = "tag value"

    val sampleDataValue = 0.0
    val sampleDataDate = ZonedDateTime.parse("2020-01-01T14:00:00Z")

    fun sampleDataType(
        name: String = sampleDataTypeName,
        description: String = sampleDataTypeDescription,
        unit: String = sampleDataTypeUnit
    ): DataType {
        val ret = DataType()
        ret.name = name
        ret.description = description
        ret.unit = unit
        return ret
    }

    fun sampleTag(name: String = sampleTagName, description: String = sampleTagDescription): Tag {
        val ret = Tag()
        ret.name = name
        ret.description = description
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

    fun sampleData(
        value: Double = sampleDataValue,
        date: ZonedDateTime = sampleDataDate,
        asset: Asset,
        dataType: DataType
    ): Data {
        val ret = Data()
        ret.value = value
        ret.timestamp = date
        ret.asset = asset
        ret.dataType = dataType
        return ret
    }
}
