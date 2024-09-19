package com.raxors.wowbase.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raxors.wowbase.ui.creatures.creature_list.CreatureListScreen
import com.raxors.wowbase.ui.home.HomeScreen
import com.raxors.wowbase.ui.common.LoadingScreen
import com.raxors.wowbase.ui.theme.WoWBaseTheme
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val authResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val intent = result.data
            if (result.resultCode == RESULT_OK && intent != null) {
                val authResponse = AuthorizationResponse.fromIntent(intent)
                val authException = AuthorizationException.fromIntent(intent)
                viewModel.processAuthResponse(authResponse, authException)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authService = AuthorizationService(this)
        viewModel.initAuthService(authService)

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.state.value.isLoading
        }

        setContent {
            WoWBaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootNavGraph(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        authResultLauncher = authResultLauncher
                    )
                }
            }
        }
    }
}

@Composable
fun RootNavGraph(
    modifier: Modifier,
    viewModel: MainViewModel,
    authResultLauncher: ActivityResultLauncher<Intent>
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsState()
    val authState = state.authState
    val initialRoute = if (state.authState != null && authState != null && authState.isAuthorized) {
        CreatureListScreen //HomeScreen
    } else {
        if (authState?.isAuthorized == false) {
            viewModel.doAuth(authResultLauncher)
        }
        LoadingScreen
    }

    NavHost(
        navController = navController,
        startDestination = initialRoute,
        modifier = modifier
    ) {
        composable<LoadingScreen>{
            LoadingScreen.Screen()
        }
        composable<HomeScreen> {
            HomeScreen.Screen()
        }
        composable<CreatureListScreen> {
            CreatureListScreen.Screen(navController)
        }
    }
}

