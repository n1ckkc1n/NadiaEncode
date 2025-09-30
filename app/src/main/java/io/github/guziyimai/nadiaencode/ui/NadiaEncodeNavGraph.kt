/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.guziyimai.nadiaencode.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.guziyimai.nadiaencode.ui.screens.NadiaEncodeIndex
import io.github.guziyimai.nadiaencode.ui.screens.NadiaEncodeSettings
import io.github.guziyimai.nadiaencode.ui.screens.NadiaEncodeSots

@Composable
fun NadiaEncodeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AppViewModel = viewModel(factory = AppViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val url = uiState.currentUrl
    val urlList = uiState.urlList
    NavHost(
        navController = navController,
        startDestination = NadiaEncodeScreen.Index.name,
        modifier = modifier,
    ) {
        composable(route = NadiaEncodeScreen.Index.name) {
            NadiaEncodeIndex(url)
        }

        composable(route = NadiaEncodeScreen.Sots.name) {
            NadiaEncodeSots(url)
        }

        composable(route = NadiaEncodeScreen.Settings.name) {
            NadiaEncodeSettings(
                url,
                urlList,
                onClick = viewModel::saveUrl
            )
        }
    }
}

enum class NadiaEncodeScreen {
    Index,
    Sots,
    Settings,
}
