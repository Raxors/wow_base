package com.raxors.wowbase.data.datasource.creature

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raxors.wowbase.data.api.WoWApi
import com.raxors.wowbase.utils.WoWLocale
import com.raxors.wowbase.domain.models.creature.CreatureSearch
import com.raxors.wowbase.domain.models.creature.CreatureSearch.Companion.toCreatureSearch
import retrofit2.HttpException
import java.io.IOException

class CreatureSearchDataSource(
    private val api: WoWApi,
    private val name: String? = null,
    private val orderBy: String = "id",
    private val locale: WoWLocale = WoWLocale.RU_RU
) : PagingSource<Int, CreatureSearch>() {

    override fun getRefreshKey(state: PagingState<Int, CreatureSearch>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CreatureSearch> =
        try {
            val page = params.key ?: 1
            val response = api.searchCreatures(name = name, orderBy = orderBy, page = page)
            LoadResult.Page(
                data = response.results.map {
                    var imagePath = ""
                    it.data?.creatureDisplays?.getOrNull(0)?.id?.let { creatureDisplayId ->
                        val creatureDisplay = api.getCreatureDisplay(creatureDisplayId)
                        imagePath = creatureDisplay.assets?.getOrNull(0)?.value ?: ""
                    }
                    it.toCreatureSearch(locale, imagePath)
                },
                prevKey = if (response.page > 1) response.page - 1 else null,
                nextKey = if (response.page < response.pageCount) response.page + 1 else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

}
