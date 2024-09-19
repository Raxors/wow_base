package com.raxors.wowbase.data.models.creature


import com.raxors.wowbase.utils.WoWLocale
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatureDataResponse(
    @SerialName("creature_displays")
    val creatureDisplays: List<CreatureDisplayResponse>? = listOf(),
    @SerialName("is_tameable")
    val isTameable: Boolean? = false,
    @SerialName("name")
    val name: Map<WoWLocale, String>? = null,
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("type")
    val type: CreatureTypeResponse? = CreatureTypeResponse(),
    @SerialName("family")
    val family: CreatureFamilyResponse? = CreatureFamilyResponse()
)