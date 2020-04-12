package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.repositories.DataTypeRepository
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
internal class DataTypeTest {

    @Inject
    lateinit var dataTypeRepository: DataTypeRepository

    val sampleName = "test name"
    val sampleDescription = "test description"
    val sampleUnit = "test unit"

    fun sampleDataType(): DataType {
        val ret = DataType()
        ret.name = sampleName
        ret.description = sampleDescription
        ret.unit = sampleUnit
        return ret
    }

    @AfterEach
    fun cleanDataTypes() {
        dataTypeRepository.deleteAll()
    }

    @Test
    fun `data type basic crud operations`() {
        val dataType = sampleDataType()

        dataType.persist()

        val dataTypes = dataTypeRepository.findAll()
        assertEquals(1, dataTypes.count())

        val res: DataType = dataTypes.firstResult()
        assertEquals(dataType.name, res.name)
        assertEquals(dataType.description, res.description)
        assertEquals(dataType.unit, res.unit)

        res.name = "another test name"
        res.persist()
        val newRes: DataType = dataTypeRepository.findById(res.id)
        assertEquals(res.name, newRes.name)

        res.delete()
        assertEquals(0, dataTypeRepository.count())
    }

    @Test
    fun `data type byName methods`() {
        val dataType = sampleDataType()
        dataType.persist()

        val res = dataTypeRepository.findByName(dataType.name)
        assertEquals(dataType.name, res?.name)

        dataTypeRepository.deleteByName(dataType.name)
        assertEquals(0, dataTypeRepository.count())

        val empty = dataTypeRepository.findByName("nothing")
        assertNull(empty)
    }

    @Test
    @Throws(Exception::class)
    fun `dataType check name uniqueness`() {
        sampleDataType().persist()
            assertThrows<PersistenceException> {
            val dt = sampleDataType()
            dt.persist()
            dt.flush()
        }
    }
}
