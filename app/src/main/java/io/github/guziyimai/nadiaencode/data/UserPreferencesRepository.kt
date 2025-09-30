package io.github.guziyimai.nadiaencode.data

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.guziyimai.nadiaencode.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    private val application: Application
) {

    private companion object {
        // 使用 booleanPreferencesKey() 函数可定义一个键并向其传递名称 is_linear_layout。
        // 与 SQL 表的名称类似，这个键需要使用下划线格式。此键用于访问布尔值，以指明是否应显示线性布局
        val CURRENT_URL = stringPreferencesKey("current_url")
        const val TAG = "UserPreferencesRepo"
    }

    // 使用映射函数将 Flow<Preferences> 转换为 Flow<Boolean>
    val currentUrl: Flow<String> = dataStore.data
        // 由于 DataStore 可从文件中读取和向其中写入数据，访问 DataStore 时可能会发生 IOExceptions。
        // 使用 catch{} 运算符来捕获异常并处理这些故障
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                // 通过发出 emptyPreferences()，映射函数仍然可以映射到默认值
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[CURRENT_URL] ?: application.resources.getStringArray(R.array.url_list)[0]
        }

    suspend fun saveUrl(currentUrl: String) {
        dataStore.edit {
            // 通过将 lambda 传递给 edit() 方法来在 DataStore 中创建和修改值。
            // 系统会向 lambda 传递 MutablePreferences 的实例，您可以使用该实例来更新 DataStore 中的值
                preferences ->
            preferences[CURRENT_URL] = currentUrl
        }
    }
}