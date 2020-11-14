package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
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
        DataType.deleteAll()
    }

    @Test
    fun `data type basic crud operations`() {
        val dataType = sampleDataType()

        dataType.persist()

        val dataTypes = DataType.findAll()
        assertEquals(1, dataTypes.count())

        val res: DataType = dataTypes.firstResult()!!
        assertEquals(dataType.name, res.name)
        assertEquals(dataType.description, res.description)
        assertEquals(dataType.unit, res.unit)

        res.name = "another test name"
        res.persist()
        val newRes: DataType = DataType.findById(res.id)!!
        assertEquals(res.name, newRes.name)

        res.delete()
        assertEquals(0, DataType.count())
    }

    @Test
    fun `data type byName methods`() {
        val dataType = sampleDataType()
        dataType.persist()

        val res = DataType.findByName(dataType.name)
        assertEquals(dataType.name, res?.name)

        DataType.deleteByName(dataType.name)
        assertEquals(0, DataType.count())

        val empty = DataType.findByName("nothing")
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
