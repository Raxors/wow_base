package com.raxors.wowbase.data.models.creature


import com.raxors.wowbase.utils.WoWLocale
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatureTypeResponse(
    @SerialName("name")
    val name: Map<WoWLocale, String>? = null,
    @SerialName("id")
    val id: Int? = 0
)