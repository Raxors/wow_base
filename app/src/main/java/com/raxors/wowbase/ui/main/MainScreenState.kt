package com.raxors.wowbase.ui.main

import net.openid.appauth.AuthState

data class MainScreenState(
    val isLoading: Boolean = true,
    val authState: AuthState? = null,
    val error: Exception? = null
)