package benchmark

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.Asset
import io.wyki.aggro.storage.entities.Data
import io.wyki.aggro.storage.repositories.DataTypeRepository
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.transaction.Transactional
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@QuarkusTest
@Transactional
@Tag("benchmark")
class DataReadTest {

    @Inject
    lateinit var dataTypeRepository: DataTypeRepository

    @Test
    fun averagePerHour() {
        val dt = dataTypeRepository.findByName(DataProvisioning.electricityConsumptionName)!!
        val asset = Asset.findByName(DataProvisioning.assetOneName)!!
        println("Time ${ZonedDateTime.now()}")
        val data = Data.findByAssetAndTypeBucketPerHour(
            asset,
            dt,
            ZonedDateTime.parse("2019-01-14T00:00:00Z"),
                ZonedDateTime.parse("2019-01-22T00:00:00Z")
        )
    }
}
