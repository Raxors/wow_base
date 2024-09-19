package com.raxors.wowbase.data.api

import com.raxors.wowbase.data.models.PageResponse
import com.raxors.wowbase.data.models.creature.CreatureDisplayResponse
import com.raxors.wowbase.data.models.creature.CreatureSearchResponse
import com.raxors.wowbase.data.models.media.MediaResponse
import com.raxors.wowbase.utils.WoWLocale
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WoWApi {

    /**
    Creatures WoW API
    **/
    @GET("data/wow/search/creature")
    suspend fun searchCreatures(
        @Query("namespace") namespace: String = "static-eu",
        @Query("name.en_US") name: String? = null,
        @Query("orderby") orderBy: String = "id",
        @Query("_page") page: Int = 1,
        @Query("_pageSize") pageSize: Int = 20
    ): PageResponse<CreatureSearchResponse>

    @GET("/data/wow/media/creature-display/{creatureDisplayId}")
    suspend fun getCreatureDisplay(
        @Path("creatureDisplayId") creatureDisplayId: Int,
        @Query("namespace") namespace: String = "static-eu",
        @Query("locale") locale: WoWLocale = WoWLocale.EN_US
    ): MediaResponse

}