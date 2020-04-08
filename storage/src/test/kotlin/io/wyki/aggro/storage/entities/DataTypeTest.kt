package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.repositories.DataTypeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.transaction.Transactional

@QuarkusTest
@Transactional
internal class DataTypeTest {

    @Inject
    lateinit var dataTypeRepository: DataTypeRepository

    @Test
    fun create_data_type() {
        val dataType = DataType()
        dataType.name = "test name"
        dataType.description = "test description"
        dataType.unit = "test unit"

        dataType.persist()

        val dataTypes = dataTypeRepository.findAll()
        assertEquals(1, dataTypes.count())
        val res: DataType = dataTypes.firstResult()
        assertEquals(dataType.name, res.name)
        assertEquals(dataType.description, res.description)
        assertEquals(dataType.unit, res.unit)
    }
}
