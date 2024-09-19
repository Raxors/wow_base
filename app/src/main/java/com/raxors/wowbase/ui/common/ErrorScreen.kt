package com.raxors.wowbase.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.raxors.wowbase.core.base.BaseScreen
import kotlinx.serialization.Serializable

@Serializable
object ErrorScreen : BaseScreen {
    @Composable
    fun Screen(error: Exception) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(error.message ?: "Unknown error")
        }
    }
}