package benchmark

import io.quarkus.test.junit.QuarkusTest
import io.wyki.aggro.storage.entities.Asset
import io.wyki.aggro.storage.entities.Data
import io.wyki.aggro.storage.entities.DataType
import io.wyki.aggro.storage.repositories.DataTypeRepository
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.transaction.Transactional
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.fail

// @BenchmarkQuarkusTest
@QuarkusTest
@Transactional
// @ConfigProperties(prefix = "benchmark")
@Tag("provisioning")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @ConfigProperty(name = "quarkus.profile", defaultValue = "benchmark")
class DataProvisioning {

    @Inject
    lateinit var flyway: Flyway
    @Inject
    lateinit var dataTypeRepository: DataTypeRepository

    companion object {
        const val electricityConsumptionName = "ELECTRICITY_CONSUMPTION"
        const val temperatureName = "TEMPERATURE"
        const val pressureName = "PRESSURE"

        const val assetOneName = "one"
        const val assetTwoName = "two"
        const val assetThreeName = "three"
        const val assetFourName = "four"
    }

    var dtElectricityConsumption = DataType(name = electricityConsumptionName, unit = "W")
    var dtTemperature = DataType(name = temperatureName, unit = "°C")
    var dtPressure = DataType(name = pressureName, unit = "hPa")
    var dataTypes = mutableMapOf(
        Pair(dtElectricityConsumption.name, dtElectricityConsumption),
        Pair(dtTemperature.name, dtTemperature),
        Pair(dtPressure.name, dtPressure)
    )

    var assetOne = Asset(assetOneName, dataTypes = mutableSetOf(dtElectricityConsumption, dtTemperature, dtPressure))
    var assetTwo = Asset(assetTwoName, dataTypes = mutableSetOf(dtElectricityConsumption, dtTemperature))
    var assetThree = Asset(assetThreeName, dataTypes = mutableSetOf(dtElectricityConsumption, dtPressure))
    var assetFour = Asset(assetFourName, dataTypes = mutableSetOf(dtElectricityConsumption))
    var assets = mutableMapOf(
        Pair(assetOne.name, assetOne),
        Pair(assetTwo.name, assetTwo),
        Pair(assetThree.name, assetThree),
        Pair(assetFour.name, assetFour)
    )

    fun initDataTypes() {
        for (dataType in dataTypes) {
            val row = dataTypeRepository.findByName(dataType.key)
            if (row != null) {
                dataTypes[dataType.key] = row
            } else {
                dataType.value.persist()
            }
        }
    }

    fun initAsset() {
        for (asset in assets) {
            val row = Asset.findByName(asset.key)
            if (row != null) {
                assets[asset.key] = row
            } else {
                asset.value.persist()
            }
        }
    }

    fun generateData(from: ZonedDateTime, to: ZonedDateTime, step: Long = 5) {
        if (step < 1) {
            fail("step must be greater than 0")
        }
        for (date in from.toEpochSecond()..to.toEpochSecond() step (24 * 60 * 60)) {
            val newFrom = ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneOffset.UTC)
            println(newFrom)
            generateElectricityConsumptionData(newFrom, newFrom.plusDays(1), step)
        }
    }

    private fun generateElectricityConsumptionData(
        from: ZonedDateTime,
        to: ZonedDateTime,
        step: Long
    ) {
        val dt = dataTypes[dtElectricityConsumption.name]!!
        val elecAssets = assets
            .filter { it.value.dataTypes.contains(dt) }
            .map { it.value }
        val data: MutableSet<Data> = mutableSetOf()
        for (date in from.toEpochSecond()..to.toEpochSecond() step (step * 60)) {
            val dataTimestamp = ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneOffset.UTC)
            val value = (date % 3600).toDouble()
            elecAssets.forEach {
                data.add(Data(value, dataTimestamp, it, dt))
            }
        }
        data.forEach {
            it.persist()
            it.flush()
        }
    }

    // @BeforeAll
    // fun cleanDatabase() {
    //     flyway.clean()
    //     flyway.migrate()
    // }

    @Test
    fun provisionData() {
        initDataTypes()
        initAsset()
        generateData(
            ZonedDateTime.parse("2019-01-01T00:00:00Z"),
            ZonedDateTime.parse("2019-02-01T00:00:00Z")
        )
    }
}
