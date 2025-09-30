package io.github.guziyimai.nadiaencode

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.github.guziyimai.nadiaencode.data.UserPreferencesRepository

private const val LAYOUT_PREFERENCE_NAME = "uri_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    // 偏好文件名称
    name = LAYOUT_PREFERENCE_NAME
)

class NadiaEncodeApplication : Application() {
    companion object {
        const val USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64; rv:145.0) Gecko/20100101 Firefox/145.0"
    }

    val userPreferencesRepository by lazy {
        UserPreferencesRepository(dataStore,this)
    }

}