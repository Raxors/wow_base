package com.raxors.wowbase.data.repository

import com.raxors.wowbase.data.api.WoWApi
import com.raxors.wowbase.domain.repository.WoWBaseRepo

class WoWBaseRepoImpl(
    private val api: WoWApi
) : WoWBaseRepo {

}