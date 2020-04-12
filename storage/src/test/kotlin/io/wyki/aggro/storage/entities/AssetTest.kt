package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleDataType
import io.wyki.aggro.storage.entities.SampleEntities.sampleTag
import io.wyki.aggro.storage.entities.SampleEntities.sampleTagValue
import io.wyki.aggro.storage.repositories.AssetRepository
import io.wyki.aggro.storage.repositories.DataTypeRepository
import io.wyki.aggro.storage.repositories.TagRepository
import io.wyki.aggro.storage.repositories.TagValueRepository
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

    @Inject
    lateinit var dataTypeRepository: DataTypeRepository
    @Inject
    lateinit var tagRepository: TagRepository
    @Inject
    lateinit var tagValueRepository: TagValueRepository
    @Inject
    lateinit var assetRepository: AssetRepository

    @AfterEach
    fun cleanDatabase() {
        tagValueRepository.deleteAll()
        tagRepository.deleteAll()
        assetRepository.deleteAll()
        dataTypeRepository.deleteAll()
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
        val assets = assetRepository.findAll()
        assertEquals(1, assets.count())
        val res: Asset = assets.firstResult()
        assertEquals(asset.name, res.name)
        assertTrue(asset.tags.contains(tagValue))
        assertTrue(asset.dataTypes.contains(dataType))

        // Update entity
        res.name = "another asset name"
        res.persist()
        val newRes: Asset = assetRepository.findById(res.id)
        assertEquals(res.name, newRes.name)

        // Delete entity, validate propagation
        assertEquals(1, assetRepository.count())
        res.delete()
        assertEquals(0, assetRepository.count())
        assertEquals(0, tagValueRepository.count())
        assertEquals(1, tagRepository.count())
        assertEquals(1, dataTypeRepository.count())
    }

    @Test
    fun `asset byName methods`() {
        val asset = sampleAsset()
        asset.persist()

        val res = assetRepository.findByName(asset.name)
        assertEquals(asset.name, res?.name)

        assetRepository.deleteByName(asset.name)
        assertEquals(0, dataTypeRepository.count())

        val empty = assetRepository.findByName("nothing")
        assertNull(empty)
    }

    @Test
    fun `asset dataType validate relation`() {
        val dataType = sampleDataType()

        val asset = sampleAsset()
        asset.dataTypes.add(dataType)
        asset.persist()

        assertEquals(1, assetRepository.count())
        assertEquals(1, dataTypeRepository.count())

        val asset2 = sampleAsset("another asset")
        asset2.dataTypes.add(dataType)
        asset2.persist()

        assertEquals(2, assetRepository.count())
        assertEquals(1, dataTypeRepository.count())
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

        assertEquals(10, assetRepository.findByDataType(dtDefault).size)
        assertEquals(5, assetRepository.findByDataType(dtConsumption).size)
        assertEquals(4, assetRepository.findByDataTypeName(dtProduction.name).size)
        assertEquals(0, assetRepository.findByDataTypeName("nothing").size)
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

        assertEquals(10, assetRepository.findByTag(location).size)
        assertEquals(10, assetRepository.findByTagName(enabled.name).size)
        assertEquals(5, assetRepository.findByTagAndValue(enabled, "true").size)
        assertEquals(5, assetRepository.findByTagNameAndValue(enabled.name, "false").size)
    }
}
