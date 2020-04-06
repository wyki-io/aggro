package io.wyki.aggro.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.wyki.aggro.app.generated.model.AggregationType
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.Arrays
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
        val expected = om.writeValueAsString(
            Arrays.asList(
                AggregationType()
                    .name("AVERAGE")
                    .description("Aggregate values on an average basis")
            )
        )
        RestAssured.given().`when`()["/aggregations"]
            .then().statusCode(200).body(CoreMatchers.`is`(expected))
    }
}
