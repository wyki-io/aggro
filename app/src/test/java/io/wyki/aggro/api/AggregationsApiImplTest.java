package io.wyki.aggro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.wyki.aggro.app.generated.model.AggregationType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@Tag("integration")
public class AggregationsApiImplTest {
    private final ObjectMapper om;

    @Inject
    public AggregationsApiImplTest(ObjectMapper om) {
        this.om = om;
    }

    @Test
    public void testHelloEndpoint() throws Exception {
        var expected = om.writeValueAsString(Arrays.asList(new AggregationType()
                .name("AVERAGE")
                .description("Aggregate values on an average basis")));
        given().when().get("/aggregations")
                .then().statusCode(200).body(is(expected));
    }
}
