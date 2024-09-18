package com.raxors.wowbase.data.api

import com.raxors.wowbase.data.models.AuthToken
import com.raxors.wowbase.utils.BATTLE_NET_CLIENT_ID
import com.raxors.wowbase.utils.BATTLE_NET_CLIENT_SECRET
import com.raxors.wowbase.utils.BATTLE_NET_GRANT_TYPE
import com.raxors.wowbase.utils.BATTLE_NET_REDIRECT_URI
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BattleNetApi {

    @POST("token")
    @FormUrlEncoded
    suspend fun getAuthToken(
        @Field("redirect_uri") redirectUri: String = BATTLE_NET_REDIRECT_URI,
        @Field("grant_type") grantType: String = BATTLE_NET_GRANT_TYPE,
        @Field("code") code: String,
        @Field("client_id") clientId: String = BATTLE_NET_CLIENT_ID,
        @Field("client_secret") clientSecret: String = BATTLE_NET_CLIENT_SECRET
    ): AuthToken

}