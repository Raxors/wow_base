package com.raxors.wowbase.domain.interactors

import com.raxors.wowbase.domain.repository.WoWBaseRepo

class CreatureInteractor(
    private val repo: WoWBaseRepo,
) {

    suspend fun searchCreatures(name: String? = null, orderBy: String = "id") =
        repo.searchCreatures(name, orderBy)

}