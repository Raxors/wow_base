package com.raxors.wowbase.data.models.creature


import com.raxors.wowbase.data.models.KeyResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatureSearchResponse(
    @SerialName("key")
    val key: KeyResponse? = KeyResponse(),
    @SerialName("data")
    val `data`: CreatureDataResponse? = CreatureDataResponse()
)