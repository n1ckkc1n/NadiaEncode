package io.github.guziyimai.nadiaencode.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.guziyimai.nadiaencode.ui.NadiaEncodeScreen

@Composable
fun NadiaEncodeBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentRoute: String
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BarItem("离线版", Icons.Filled.Home, onClick = {
            navController.navigate(NadiaEncodeScreen.Index.name) {
                popUpTo(currentRoute) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }),
        BarItem("Sots", Icons.Filled.Share, onClick = {
            navController.navigate(NadiaEncodeScreen.Sots.name) {
                popUpTo(currentRoute) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }),
        BarItem("切换域名", Icons.Filled.Settings, onClick = {
            navController.navigate(NadiaEncodeScreen.Settings.name) {
                popUpTo(currentRoute) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        })
    )

    NavigationBar(modifier) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    item.onClick()
                }
            )
        }
    }
}

data class BarItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun NadiaEncodeBottomBarPreview() {
    NadiaEncodeBottomBar(
        modifier = Modifier.fillMaxWidth(),
        navController = rememberNavController(),
        currentRoute = ""
    )
}