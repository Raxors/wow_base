package com.raxors.wowbase.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.raxors.wowbase.data.api.WoWApi
import com.raxors.wowbase.data.datasource.creature.CreatureSearchDataSource
import com.raxors.wowbase.domain.models.creature.CreatureSearch
import com.raxors.wowbase.domain.repository.WoWBaseRepo
import com.raxors.wowbase.utils.WoWLocale
import javax.inject.Singleton

@Singleton
class WoWBaseRepoImpl(
    private val api: WoWApi,
//    private val settings: WoWBaseSettings
) : WoWBaseRepo {

    override suspend fun searchCreatures(
        name: String?,
        orderBy: String,
    ): Pager<Int, CreatureSearch> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { CreatureSearchDataSource(
                api = api,
                name = name,
                orderBy = orderBy,
                locale = WoWLocale.RU_RU // TODO get locale from settings
            ) }
        )

}