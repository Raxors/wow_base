package com.raxors.wowbase.domain.models.creature

import com.raxors.wowbase.data.models.creature.CreatureSearchResponse
import com.raxors.wowbase.domain.models.creature.CreatureFamily.Companion.toCreatureFamily
import com.raxors.wowbase.domain.models.creature.CreatureType.Companion.toCreatureType
import com.raxors.wowbase.utils.WoWLocale

data class CreatureSearch(
    val id: Int,
    val name: String?,
    val type: CreatureType?,
    val family: CreatureFamily?,
    val imagePath: String? = null
) {
    companion object {
        fun CreatureSearchResponse.toCreatureSearch(locale: WoWLocale) = CreatureSearch(
            id = this.data?.id ?: 0,
            name = this.data?.name?.get(locale),
            type = this.data?.type?.toCreatureType(locale),
            family = this.data?.family?.toCreatureFamily(locale),
        )

        fun CreatureSearchResponse.toCreatureSearch(locale: WoWLocale, imagePath: String) = CreatureSearch(
            id = this.data?.id ?: 0,
            name = this.data?.name?.get(locale),
            type = this.data?.type?.toCreatureType(locale),
            family = this.data?.family?.toCreatureFamily(locale),
            imagePath = imagePath
        )
    }
}
