package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.repositories.TagRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Exception
import javax.inject.Inject
import javax.persistence.PersistenceException
import javax.transaction.Transactional

@QuarkusTest
@Transactional
internal class TagTest {

    @Inject
    lateinit var tagRepository: TagRepository

    val sampleName = "test name"
    val sampleDescription = "test description"

    fun sampleTag() : Tag {
        val ret = Tag()
        ret.name = sampleName
        ret.description = sampleDescription
        return ret
    }

    @AfterEach
    fun cleanTags() {
        tagRepository.deleteAll()
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
        assertEquals(tag.name, res.name)

        tagRepository.deleteByName(tag.name)
        assertEquals(0, tagRepository.count())
    }

    @Test
    @Throws(Exception::class)
    fun `tag check name uniqueness`() {
        sampleTag().persist()
        org.junit.jupiter.api.assertThrows<PersistenceException> {
            val tag = sampleTag()
            tag.persist()
            tag.flush()
        }
    }
}
