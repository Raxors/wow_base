package com.raxors.wowbase.ui.main

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raxors.wowbase.domain.repository.WoWBaseRepo
import com.raxors.wowbase.utils.AuthManager
import com.raxors.wowbase.utils.BATTLE_NET_CLIENT_SECRET
import com.raxors.wowbase.utils.OPENID_CONFIG_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val repo: WoWBaseRepo
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state
        .onStart {
            fetchAuthState()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainScreenState()
        )

    private var authService: AuthorizationService? = null

    private fun fetchAuthState() {
        viewModelScope.launch {
            val authState = authManager.readAuthState()
            _state.update { it.copy(authState = authState, isLoading = !authState.isAuthorized) }
        }
    }

    fun initAuthService(authService: AuthorizationService) {
        this.authService = authService
    }

    fun doAuth(authResultLauncher: ActivityResultLauncher<Intent>) {
        AuthorizationServiceConfiguration.fetchFromUrl(
            Uri.parse(OPENID_CONFIG_URL),
            AuthorizationServiceConfiguration.RetrieveConfigurationCallback { serviceConfig, exception ->
                if (exception != null || serviceConfig == null) {
                    _state.update { it.copy(error = exception) }
                    return@RetrieveConfigurationCallback
                }
                authManager.setAuthState(serviceConfig)
                val authRequest = authManager.createAuthRequest(serviceConfig)
                val authIntent = authService?.getAuthorizationRequestIntent(authRequest)
                authIntent?.let { authResultLauncher.launch(it) }
            })
    }

    fun processAuthResponse(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) {
        authManager.getAuthState()?.update(authResponse, authException)
        if (authException != null || authResponse == null) {
            _state.update { it.copy(error = authException) }
            return
        }
        val clientAuth: ClientAuthentication = ClientSecretBasic(BATTLE_NET_CLIENT_SECRET)
        authService?.performTokenRequest(
            authResponse.createTokenExchangeRequest(),
            clientAuth
        ) { tokenResponse, tokenExeption ->
            authManager.getAuthState()?.update(tokenResponse, tokenExeption)
            viewModelScope.launch {
                updateAuthState(authManager.getAuthState())
            }
            if (tokenExeption != null) {
                _state.update { it.copy(error = tokenExeption) }
                return@performTokenRequest
            }
            authService?.let {
                authManager.getAuthState()?.performActionWithFreshTokens(it) { accessToken, idToken, exeption ->
                    if (exeption != null) {
                        _state.update { it.copy(error = exeption) }
                        return@performActionWithFreshTokens;
                    }
                    accessToken?.let { /* add token to responses header */ }
                }
            }
        }
    }

    private suspend fun updateAuthState(authState: AuthState?) {
        _state.update { it.copy(authState = authManager.getAuthState(), isLoading = false) }
        authManager.saveAuthState(authState)
    }

}