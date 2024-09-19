package com.raxors.wowbase.data.models.creature


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatureDisplayResponse(
    @SerialName("id")
    val id: Int? = null
)