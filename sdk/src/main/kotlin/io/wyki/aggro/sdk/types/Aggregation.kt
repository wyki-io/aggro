package io.wyki.aggro.sdk.types

enum class Aggregation(val description: String) {
    AVERAGE("Aggregate values on an average basis"),
    MINIMUM("Give the minimum value in the provided time interval"),
    MAXIMUM("Give the maximum value in the provided time intervale")
}
