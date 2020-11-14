package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleTag
import io.wyki.aggro.storage.entities.SampleEntities.sampleTagValue
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

    @AfterEach
    fun cleanTags() {
        TagValue.deleteAll()
        Tag.deleteAll()
        Asset.deleteAll()
    }

    @Test
    fun `tag basic crud operations`() {
        val tag = sampleTag()

        tag.persist()

        val tags = Tag.findAll()
        assertEquals(1, tags.count())

        val res: Tag = tags.firstResult()!!
        assertEquals(tag.name, res.name)
        assertEquals(tag.description, res.description)

        res.name = "another test name"
        res.persist()
        val newRes: Tag = Tag.findById(res.id)!!
        assertEquals(res.name, newRes.name)

        res.delete()
        assertEquals(0, Tag.count())
    }

    @Test
    fun `tag byName methods`() {
        val tag = sampleTag()
        tag.persist()

        val res = Tag.findByName(tag.name)
        assertEquals(tag.name, res?.name)

        Tag.deleteByName(tag.name)
        assertEquals(0, Tag.count())

        val empty = Tag.findByName("nothing")
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

        assertEquals(1, Asset.count())
        assertEquals(1, Tag.count())
        assertEquals(1, TagValue.count())

        val tag2 = sampleTag("another")
        val tagValue2 = sampleTagValue(asset = asset, tag = tag2)
        tagValue2.persist()

        assertEquals(1, Asset.count())
        assertEquals(2, Tag.count())
        assertEquals(2, TagValue.count())
    }
}
