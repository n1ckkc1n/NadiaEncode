package io.github.guziyimai.nadiaencode.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.guziyimai.nadiaencode.ui.components.NadiaEncodeBottomBar

@Composable
fun NadiaEncodeApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: NadiaEncodeScreen.Index.name

    Scaffold(
        bottomBar = {
            NadiaEncodeBottomBar(
                navController = navController,
                currentRoute = currentRoute,
            )
        },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            NadiaEncodeNavGraph(navController = navController)
        }
    }
}