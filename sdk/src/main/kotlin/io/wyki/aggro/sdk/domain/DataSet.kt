package io.wyki.aggro.sdk.domain

import java.time.ZonedDateTime

data class DataSet(
    val dataType: DataType,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
    val interval: Interval,
    val aggregation: Aggregation,
    val data: List<Data>
)
