package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleTag
import io.wyki.aggro.storage.entities.SampleEntities.sampleTagValue
import io.wyki.aggro.storage.repositories.TagRepository
import io.wyki.aggro.storage.repositories.TagValueRepository
import java.util.UUID
import javax.inject.Inject
import javax.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

@QuarkusTest
@Transactional
internal class TagValueTest {

    @Inject
    lateinit var tagValueRepository: TagValueRepository
    @Inject
    lateinit var tagRepository: TagRepository

    @AfterEach
    fun cleanTags() {
        tagValueRepository.deleteAll()
        tagRepository.deleteAll()
        Asset.deleteAll()
    }

    @Test
    fun `tagValue getters`() {
        val asset = sampleAsset()
        val tag = sampleTag()
        val tagValue = sampleTagValue(asset = asset, tag = tag)
        tagValue.persist()

        val tagValues = tagValueRepository.findAll()
        assertEquals(1, tagValues.count())

        val res: TagValue = tagValues.firstResult()
        assertEquals(tagValue.value, res.value)
        assertEquals(tagValue.asset, res.asset)
        assertEquals(tagValue.tag, res.tag)
        assertEquals(1, Asset.count())
        assertEquals(1, tagRepository.count())
        assertEquals(1, tagValueRepository.count())
    }

    @Test
    fun `tagValue manual id`() {
        val asset = sampleAsset()
        val tag = sampleTag()
        val tagValue = sampleTagValue(asset = asset, tag = tag)
        tagValue.persist()

        val id = UUID.randomUUID()
        assertNotEquals(id, tagValueRepository.findAll().firstResult<TagValue>().id)

        tagValue.id = id
        assertEquals(id, tagValueRepository.findAll().firstResult<TagValue>().id)
    }
}
