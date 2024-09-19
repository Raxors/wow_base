package com.raxors.wowbase.domain.repository

import androidx.paging.Pager
import com.raxors.wowbase.domain.models.creature.CreatureSearch

interface WoWBaseRepo {

    suspend fun searchCreatures(
        name: String? = null,
        orderBy: String = "id",
    ): Pager<Int, CreatureSearch>

}