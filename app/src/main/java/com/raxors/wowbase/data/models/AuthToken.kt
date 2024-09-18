package com.raxors.wowbase.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
    @SerialName("expires_in")
    val expiresIn: Int? = null,
    @SerialName("scope")
    val scope: String? = null,
    @SerialName("sub")
    val sub: String? = null
)