package com.raxors.wowbase.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.annotation.AnyThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.RegistrationResponse
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenResponse
import org.json.JSONException
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject


class AuthManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private var authState: AuthState? = null

    fun setAuthState(serviceConfig: AuthorizationServiceConfiguration) {
        authState = AuthState(serviceConfig)
    }

    fun getAuthState(): AuthState? = authState

    fun createAuthRequest(serviceConfig: AuthorizationServiceConfiguration): AuthorizationRequest =
        AuthorizationRequest.Builder(
            serviceConfig,
            BATTLE_NET_CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse(BATTLE_NET_REDIRECT_URI)
        ).setScopes("openid")
            .build()

    suspend fun readAuthState(): AuthState {
        return dataStore.data
            .map { prefs ->
                prefs[KEY_TOKEN]?.let { AuthState.jsonDeserialize(it) }
            }
            .firstOrNull() ?: AuthState()
    }

    suspend fun saveAuthState(authState: AuthState?) {
        if (authState == null) return
        dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = authState.jsonSerializeString()
        }
    }

    suspend fun onLogout() {
        dataStore.edit {
            it.remove(KEY_TOKEN)
        }
    }

    companion object {
        val KEY_TOKEN = stringPreferencesKey("key_token")
    }
}