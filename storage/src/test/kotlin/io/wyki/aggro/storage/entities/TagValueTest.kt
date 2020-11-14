package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleTag
import io.wyki.aggro.storage.entities.SampleEntities.sampleTagValue
import java.util.UUID
import javax.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

@QuarkusTest
@Transactional
internal class TagValueTest {

    @AfterEach
    fun cleanTags() {
        TagValue.deleteAll()
        Tag.deleteAll()
        Asset.deleteAll()
    }

    @Test
    fun `tagValue getters`() {
        val asset = sampleAsset()
        val tag = sampleTag()
        val tagValue = sampleTagValue(asset = asset, tag = tag)
        tagValue.persist()

        val tagValues = TagValue.findAll()
        assertEquals(1, tagValues.count())

        val res: TagValue = tagValues.firstResult()!!
        assertEquals(tagValue.value, res.value)
        assertEquals(tagValue.asset, res.asset)
        assertEquals(tagValue.tag, res.tag)
        assertEquals(1, Asset.count())
        assertEquals(1, Tag.count())
        assertEquals(1, TagValue.count())
    }

    @Test
    fun `tagValue manual id`() {
        val asset = sampleAsset()
        val tag = sampleTag()
        val tagValue = sampleTagValue(asset = asset, tag = tag)
        tagValue.persist()

        val id = UUID.randomUUID()
        assertNotEquals(id, TagValue.findAll().firstResult()!!.id)

        tagValue.id = id
        assertEquals(id, TagValue.findAll().firstResult()!!.id)
    }
}
