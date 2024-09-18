package com.raxors.wowbase.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthenticationService @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun isAuthenticated(): Flow<Boolean> {
        return dataStore.data.map {
            it.contains(KEY_TOKEN)
        }
    }

    suspend fun store(token: String) {
        dataStore.edit {
            it[KEY_TOKEN] = token
        }
    }

    suspend fun getToken(): String {
        return dataStore.data
            .map { it[KEY_TOKEN] }
            .firstOrNull()
            ?: throw IllegalArgumentException("no token stored")
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