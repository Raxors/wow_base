package com.raxors.wowbase.data.models.media


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaResponse(
    @SerialName("_links")
    val links: LinksResponse? = null,
    @SerialName("assets")
    val assets: List<AssetResponse?>? = null,
    @SerialName("id")
    val id: Int? = null
)