package com.raxors.wowbase.data.models.media


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksResponse(
    @SerialName("self")
    val self: SelfResponse? = null
)