package com.raxors.wowbase.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageResponse<T>(
    @SerialName("page")
    val page: Int,
    @SerialName("pageSize")
    val pageSize: Int?,
    @SerialName("maxPageSize")
    val maxPageSize: Int,
    @SerialName("pageCount")
    val pageCount: Int,
    @SerialName("resultCountCapped")
    val resultCountCapped: Boolean? = null,
    @SerialName("results")
    val results: List<T> = listOf()
)