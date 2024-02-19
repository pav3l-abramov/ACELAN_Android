package com.example.acelanandroid.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.acelanandroid.ACTIVE_USER
import com.example.acelanandroid.EMAIL_USER
import com.example.acelanandroid.PASSWORD_USER
import com.example.acelanandroid.TOKEN_USER
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager @Inject constructor( @ApplicationContext val contex: Context) {
    suspend fun saveDataUser(userData: UserData) {
        contex.dataStore.edit { pref ->
            pref[stringPreferencesKey(EMAIL_USER)] = userData.email
            pref[stringPreferencesKey(PASSWORD_USER)] = userData.password
            pref[stringPreferencesKey(TOKEN_USER)] = userData.token
            pref[booleanPreferencesKey(ACTIVE_USER)] = userData.isActive
        }
    }

    fun getDataUser() = contex.dataStore.data.map { pref ->
         UserData(
            pref[stringPreferencesKey(EMAIL_USER)] ?: "",
            pref[stringPreferencesKey(PASSWORD_USER)] ?: "",
            pref[stringPreferencesKey(TOKEN_USER)] ?: "",
            pref[booleanPreferencesKey(ACTIVE_USER)] ?: false
        )
    }

    suspend fun deleteDataUser() {
        contex.dataStore.edit { pref ->
            pref[stringPreferencesKey(EMAIL_USER)] = ""
            pref[stringPreferencesKey(PASSWORD_USER)] = ""
            pref[stringPreferencesKey(TOKEN_USER)] = ""
            pref[booleanPreferencesKey(ACTIVE_USER)] = false
        }
    }
}