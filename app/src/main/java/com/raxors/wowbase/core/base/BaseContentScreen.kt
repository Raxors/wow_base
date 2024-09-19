package com.raxors.wowbase.core.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.raxors.wowbase.ui.common.ErrorScreen
import com.raxors.wowbase.ui.common.LoadingScreen

abstract class BaseContentScreen<STATE : BaseScreenState, VM : BaseViewModel<STATE>> : BaseScreen {

    @Composable
    inline fun <reified VM : ViewModel> ScreenContent(
        navController: NavHostController,
        content: @Composable (VM, STATE) -> Unit
    ) {
        val viewModel = hiltViewModel<VM>()
        val state by (viewModel as BaseViewModel<STATE>).state.collectAsState()
        content(viewModel, state)
        with(state) {
            if (isLoading) {
                LoadingScreen.Screen()
            }
            error?.let {
                ErrorScreen.Screen(it)
            }
        }
    }

}