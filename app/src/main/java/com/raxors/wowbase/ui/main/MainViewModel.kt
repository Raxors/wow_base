package com.raxors.wowbase.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.raxors.wowbase.data.api.BattleNetApi
import com.raxors.wowbase.domain.repository.WoWBaseRepo
import com.raxors.wowbase.utils.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val repo: WoWBaseRepo
): ViewModel() {

    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    var isLoading = false

    fun checkAuthentication() {
        viewModelScope.launch {
            authenticationService.isAuthenticated().collectLatest { isAuthenticated ->
                _isAuthenticated.value = isAuthenticated
            }
        }
    }

    fun onLogin(code: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://oauth.battle.net/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

        val apiService = retrofit.create(BattleNetApi::class.java)
        viewModelScope.launch {
            val token = apiService.getAuthToken(code = code)
            authenticationService.store(token.accessToken ?: "")
            isLoading = false
        }
    }

}