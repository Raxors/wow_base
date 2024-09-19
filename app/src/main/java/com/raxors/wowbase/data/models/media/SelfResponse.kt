package com.raxors.wowbase.data.models.media


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SelfResponse(
    @SerialName("href")
    val href: String? = null
)