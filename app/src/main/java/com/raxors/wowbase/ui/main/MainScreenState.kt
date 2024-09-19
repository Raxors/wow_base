package com.raxors.wowbase.ui.main

import com.raxors.wowbase.core.base.BaseScreenState
import net.openid.appauth.AuthState

data class MainScreenState(
    val authState: AuthState? = null,
    override val isLoading: Boolean = true,
    override val error: Exception? = null
) : BaseScreenState