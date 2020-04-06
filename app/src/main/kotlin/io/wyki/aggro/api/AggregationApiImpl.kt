package io.wyki.aggro.api

import io.wyki.aggro.app.generated.api.AggregationsApi
import io.wyki.aggro.app.generated.model.AggregationType
import java.util.Arrays
import javax.ws.rs.core.Response

class AggregationsApiImpl : AggregationsApi {
    override fun listAggregations(): Response {
        return Response.status(Response.Status.OK)
            .entity(
                Arrays
                    .asList(
                        AggregationType()
                            .name("AVERAGE")
                            .description("Aggregate values on an average basis")
                    )
            )
            .build()
    }
}
