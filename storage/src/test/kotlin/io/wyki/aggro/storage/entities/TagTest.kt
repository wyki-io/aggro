package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleTag
import io.wyki.aggro.storage.entities.SampleEntities.sampleTagValue
import io.wyki.aggro.storage.repositories.AssetRepository
import io.wyki.aggro.storage.repositories.TagRepository
import io.wyki.aggro.storage.repositories.TagValueRepository
import javax.inject.Inject
import javax.persistence.PersistenceException
import javax.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@QuarkusTest
@Transactional
internal class TagTest {

    @Inject
    lateinit var tagRepository: TagRepository
    @Inject
    lateinit var tagValueRepository: TagValueRepository
    @Inject
    lateinit var assetRepository: AssetRepository

    @AfterEach
    fun cleanTags() {
        tagValueRepository.deleteAll()
        tagRepository.deleteAll()
        assetRepository.deleteAll()
    }

    @Test
    fun `tag basic crud operations`() {
        val tag = sampleTag()

        tag.persist()

        val tags = tagRepository.findAll()
        assertEquals(1, tags.count())

        val res: Tag = tags.firstResult()
        assertEquals(tag.name, res.name)
        assertEquals(tag.description, res.description)

        res.name = "another test name"
        res.persist()
        val newRes: Tag = tagRepository.findById(res.id)
        assertEquals(res.name, newRes.name)

        res.delete()
        assertEquals(0, tagRepository.count())
    }

    @Test
    fun `tag byName methods`() {
        val tag = sampleTag()
        tag.persist()

        val res = tagRepository.findByName(tag.name)
        assertEquals(tag.name, res?.name)

        tagRepository.deleteByName(tag.name)
        assertEquals(0, tagRepository.count())

        val empty = tagRepository.findByName("nothing")
        assertNull(empty)
    }

    @Test
    @Throws(Exception::class)
    fun `tag check name uniqueness`() {
        sampleTag().persist()
        assertThrows<PersistenceException> {
            val tag = sampleTag()
            tag.persist()
            tag.flush()
        }
    }

    @Test
    fun `tag tagValue validate relation`() {
        val asset = sampleAsset()
        val tag = sampleTag()
        tag.values = mutableSetOf()

        val tagValue = sampleTagValue(asset = asset, tag = tag)
        tagValue.persist()

        assertEquals(1, assetRepository.count())
        assertEquals(1, tagRepository.count())
        assertEquals(1, tagValueRepository.count())

        val tag2 = sampleTag("another")
        val tagValue2 = sampleTagValue(asset = asset, tag = tag2)
        tagValue2.persist()

        assertEquals(1, assetRepository.count())
        assertEquals(2, tagRepository.count())
        assertEquals(2, tagValueRepository.count())
    }
}
