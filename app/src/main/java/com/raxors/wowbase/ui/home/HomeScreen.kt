package com.raxors.wowbase.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.raxors.wowbase.navigation.BaseScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen: BaseScreen {

    @Composable
    fun Screen() {
        Text("You is authorized")
    }

}