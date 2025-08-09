package com.zikre.bazmeauliya.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//@Singleton
class DataStoreUtil @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val THEME_KEY = booleanPreferencesKey("theme")
        val MOBILE_NO = stringPreferencesKey("mobile_no")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_ID = stringPreferencesKey("user_id")
        val IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
        val JWT = stringPreferencesKey("JWT_Token")
    }

    fun getTheme(isSystemDarkTheme: Boolean): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: isSystemDarkTheme
        }

    suspend fun saveTheme(isDarkThemeEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkThemeEnabled
        }
    }

    fun getMobileNo(): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[MOBILE_NO] ?: ""
        }

    suspend fun saveMobileNo(value: String) {
        context.dataStore.edit { preferences ->
            preferences[MOBILE_NO] = value
        }
    }

    fun isLoggedIn(): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }

    suspend fun saveLoggedIn(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = value
        }
    }

    fun getUserId(): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID] ?: ""
        }

    suspend fun setUserId(value: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = value
        }
    }

    fun getUserName(): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME] ?: ""
        }

    suspend fun setUserName(value: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = value
        }
    }

    suspend fun saveToken(value: String) {
        context.dataStore.edit { preferences ->
            preferences[JWT] = value
        }
    }

    fun getJWT(): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[JWT] ?: ""
        }



}