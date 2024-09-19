package com.raxors.wowbase.domain.models.creature


import com.raxors.wowbase.data.models.creature.CreatureFamilyResponse
import com.raxors.wowbase.utils.WoWLocale
import kotlinx.serialization.Serializable

@Serializable
data class CreatureFamily(
    val id: Int,
    val name: String
) {
    companion object {
        fun CreatureFamilyResponse?.toCreatureFamily(locale: WoWLocale) = CreatureFamily(
            id = this?.id ?: 0,
            name = this?.name?.get(locale) ?: ""
        )
    }
}