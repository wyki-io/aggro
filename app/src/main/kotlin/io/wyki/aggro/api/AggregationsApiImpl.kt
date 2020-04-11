package io.wyki.aggro.api

import io.wyki.aggro.app.generated.api.AggregationsApi
import io.wyki.aggro.app.generated.model.AggregationType
import io.wyki.aggro.sdk.domain.Aggregation
import javax.ws.rs.core.Response

class AggregationsApiImpl : AggregationsApi {
    override fun listAggregations(): Response {
        val aggregations = Aggregation.values().map {
            AggregationType().name(it.name).description(it.description)
        }
        return Response.status(Response.Status.OK)
            .entity(aggregations)
            .build()
    }
}
