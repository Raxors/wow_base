package com.raxors.wowbase.domain.models.creature


import com.raxors.wowbase.data.models.creature.CreatureTypeResponse
import com.raxors.wowbase.utils.WoWLocale
import kotlinx.serialization.Serializable

@Serializable
data class CreatureType(
    val id: Int,
    val name: String
) {
    companion object {
        fun CreatureTypeResponse?.toCreatureType(locale: WoWLocale) = CreatureType(
            id = this?.id ?: 0,
            name = this?.name?.get(locale) ?: ""
        )
    }
}