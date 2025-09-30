package io.github.guziyimai.nadiaencode.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.guziyimai.nadiaencode.NadiaEncodeApplication
import io.github.guziyimai.nadiaencode.R
import io.github.guziyimai.nadiaencode.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val userPreferencesRepository: UserPreferencesRepository, application: Application
) : AndroidViewModel(application) {
    val urlList = application.resources.getStringArray(R.array.url_list).toList()

    val uiState: StateFlow<AppUiState> = userPreferencesRepository.currentUrl.map {
            currentUrl ->
        AppUiState(
            currentUrl,
            urlList
        )
        // 使用 stateIn() 函数将 Flow 转换为 StateFlow
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppUiState(
            urlList[0],
            urlList
        )
    )

    fun saveUrl(currentUrl: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUrl(currentUrl)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as NadiaEncodeApplication
                AppViewModel(application.userPreferencesRepository, application)
            }
        }
    }
}

data class AppUiState(
    val currentUrl: String,
    val urlList: List<String>
)