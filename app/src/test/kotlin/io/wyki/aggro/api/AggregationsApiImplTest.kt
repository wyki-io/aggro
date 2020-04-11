package io.wyki.aggro.api

/* ktlint-disable import-ordering */
import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.Matchers.hasItems
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import javax.enterprise.inject.Default
import javax.inject.Inject

@QuarkusTest
@Tag("integration")
class AggregationsApiImplTest {

    @Inject
    @field: Default
    private lateinit var om: ObjectMapper

    @Test
    @Throws(Exception::class)
    fun testHelloEndpoint() {
        RestAssured.given().`when`()["/aggregations"]
            .then().statusCode(200).body(
                "name", hasItems("AVERAGE", "MINIMUM", "MAXIMUM")
            )
    }
}
