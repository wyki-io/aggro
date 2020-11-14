package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleData
import io.wyki.aggro.storage.entities.SampleEntities.sampleDataType
import javax.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
@Transactional
internal class DataTest {

    @AfterEach
    fun cleanDatabase() {
        Data.deleteAll()
        Asset.deleteAll()
        DataType.deleteAll()
    }

    @Test
    fun `data basic crud operations`() {
        // Create entities
        val dataType = sampleDataType("consumption")
        val asset = sampleAsset()
        asset.dataTypes.add(dataType)
        val data = sampleData(asset = asset, dataType = dataType)
        data.persist()

        // Retrieve entity
        val datas = Data.findAll()
        assertEquals(1, datas.count())
        val res: Data = datas.firstResult()!!
        assertEquals(data.value, res.value)
        assertEquals(data.timestamp, res.timestamp)
        assertEquals(data.asset, res.asset)
        assertEquals(data.dataType, res.dataType)

        // Update entity
        res.value = 2.0
        res.persist()
        val newRes: Data = Data.findById(res.id)!!
        assertEquals(res.value, newRes.value)

        // Delete entity, validate propagation
        assertEquals(1, Data.count())
        res.delete()
        assertEquals(0, Data.count())
        assertEquals(1, Asset.count())
        assertEquals(1, DataType.count())
    }
}
