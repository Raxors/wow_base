package com.raxors.wowbase.data.models.media


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetResponse(
    @SerialName("key")
    val key: String? = null,
    @SerialName("value")
    val value: String? = null
)