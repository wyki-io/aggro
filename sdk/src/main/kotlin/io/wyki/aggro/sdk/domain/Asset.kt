package io.wyki.aggro.sdk.domain

import java.util.UUID

data class Asset(val id: UUID, val name: String, val tags: List<Tag>, val dataTypes: List<DataType>)
