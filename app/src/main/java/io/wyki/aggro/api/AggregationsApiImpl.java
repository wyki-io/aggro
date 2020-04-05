package io.wyki.aggro.api;

import io.wyki.aggro.app.generated.api.AggregationsApi;
import io.wyki.aggro.app.generated.model.AggregationType;

import javax.ws.rs.core.Response;
import java.util.Arrays;

public class AggregationsApiImpl implements AggregationsApi {
    @Override
    public Response listAggregations() {
        Response rsp = Response.status(Response.Status.OK)
                .entity(Arrays
                        .asList(new AggregationType()
                                .name("AVERAGE")
                                .description("Aggregate values on an average basis")))
                .build();
        return rsp;
    }
}
