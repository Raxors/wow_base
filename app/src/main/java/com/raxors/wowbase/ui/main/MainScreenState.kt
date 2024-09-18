package com.raxors.wowbase.ui.main

data class MainScreenState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null
)