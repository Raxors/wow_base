package com.raxors.wowbase.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyResponse(
    @SerialName("href")
    val href: String? = null
)