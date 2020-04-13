package io.wyki.aggro.storage.entities

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.SampleEntities.sampleAsset
import io.wyki.aggro.storage.entities.SampleEntities.sampleData
import io.wyki.aggro.storage.entities.SampleEntities.sampleDataType
import io.wyki.aggro.storage.repositories.AssetRepository
import io.wyki.aggro.storage.repositories.DataRepository
import io.wyki.aggro.storage.repositories.DataTypeRepository
import javax.inject.Inject
import javax.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
@Transactional
internal class DataTest {

    @Inject
    lateinit var dataRepository: DataRepository
    @Inject
    lateinit var dataTypeRepository: DataTypeRepository
    @Inject
    lateinit var assetRepository: AssetRepository

    @AfterEach
    fun cleanDatabase() {
        dataRepository.deleteAll()
        assetRepository.deleteAll()
        dataTypeRepository.deleteAll()
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
        val datas = dataRepository.findAll()
        assertEquals(1, datas.count())
        val res: Data = datas.firstResult()
        assertEquals(data.value, res.value)
        assertEquals(data.timestamp, res.timestamp)
        assertEquals(data.asset, res.asset)
        assertEquals(data.dataType, res.dataType)

        // Update entity
        res.value = 2.0
        res.persist()
        val newRes: Data = dataRepository.findById(res.id)
        assertEquals(res.value, newRes.value)

        // Delete entity, validate propagation
        assertEquals(1, dataRepository.count())
        res.delete()
        assertEquals(0, dataRepository.count())
        assertEquals(1, assetRepository.count())
        assertEquals(1, dataTypeRepository.count())
    }
}
