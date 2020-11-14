package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleDataType
import io.wyki.aggro.storage.entities.SampleEntities.sampleTag
import io.wyki.aggro.storage.entities.SampleEntities.sampleTagValue
import javax.inject.Inject
import javax.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@QuarkusTest
@Transactional
internal class AssetTest {

    @AfterEach
    fun cleanDatabase() {
        TagValue.deleteAll()
        Tag.deleteAll()
        Asset.deleteAll()
        DataType.deleteAll()
    }

    @Test
    fun `asset basic crud operations`() {
        // Create entities
        val tag = sampleTag()
        val dataType = sampleDataType()
        val asset = sampleAsset()
        asset.dataTypes.add(dataType)
        val tagValue = sampleTagValue(asset = asset, tag = tag)
        tagValue.persist()

        // Retrieve entity
        val assets = Asset.findAll()
        assertEquals(1, assets.count())
        val res: Asset = assets.firstResult()!!
        assertEquals(asset.name, res.name)
        assertTrue(res.tags.contains(tagValue))
        assertTrue(asset.tags.contains(tagValue))
        assertTrue(asset.dataTypes.contains(dataType))

        // Update entity
        res.name = "another asset name"
        res.persist()
        val newRes: Asset = Asset.findById(res.id)!!
        assertEquals(res.name, newRes.name)

        // Delete entity, validate propagation
        assertEquals(1, Asset.count())
        res.delete()
        assertEquals(0, Asset.count())
        assertEquals(0, TagValue.count())
        assertEquals(1, Tag.count())
        assertEquals(1, DataType.count())
    }

    @Test
    fun `asset byName methods`() {
        val asset = sampleAsset()
        asset.persist()

        val res = Asset.findByName(asset.name)
        assertEquals(asset.name, res?.name)

        Asset.deleteByName(asset.name)
        assertEquals(0, DataType.count())

        val empty = Asset.findByName("nothing")
        assertNull(empty)
    }

    @Test
    fun `asset dataType validate relation`() {
        val dataType = sampleDataType()

        val asset = sampleAsset()
        asset.dataTypes.add(dataType)
        asset.persist()

        assertEquals(1, Asset.count())
        assertEquals(1, DataType.count())

        val asset2 = sampleAsset("another asset")
        asset2.dataTypes.add(dataType)
        asset2.persist()

        assertEquals(2, Asset.count())
        assertEquals(1, DataType.count())
    }

    @Test
    fun `asset find by dataType`() {
        val dtDefault = sampleDataType("default")
        val dtConsumption = sampleDataType("consumption")
        val dtProduction = sampleDataType("production")

        for (i in 0 until 10) {
            val asset = sampleAsset(i.toString())
            val dts = mutableSetOf(dtDefault)
            if (i % 2 == 0)
                dts.add(dtConsumption)
            if (i % 3 == 0)
                dts.add(dtProduction)
            asset.dataTypes = dts
            asset.persist()
        }

        assertEquals(10, Asset.findByDataType(dtDefault).size)
        assertEquals(5, Asset.findByDataType(dtConsumption).size)
        assertEquals(4, Asset.findByDataTypeName(dtProduction.name).size)
        assertEquals(0, Asset.findByDataTypeName("nothing").size)
    }

    @Test
    fun `asset find by tag`() {
        val location = sampleTag("location")
        val enabled = sampleTag("enabled")

        for (i in 0 until 10) {
            val asset = sampleAsset(i.toString())
            val tags = mutableSetOf(sampleTagValue("somewhere", asset, location))
            if (i < 5)
                tags.add(sampleTagValue("true", asset, enabled))
            else
                tags.add(sampleTagValue("false", asset, enabled))
            asset.tags = tags
            asset.persist()
        }

        assertEquals(10, Asset.findByTag(location).size)
        assertEquals(10, Asset.findByTagName(enabled.name).size)
        assertEquals(5, Asset.findByTagAndValue(enabled, "true").size)
        assertEquals(5, Asset.findByTagNameAndValue(enabled.name, "false").size)
    }
}
