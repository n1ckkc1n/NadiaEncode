package io.github.guziyimai.nadiaencode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.guziyimai.nadiaencode.ui.NadiaEncodeApp
import io.github.guziyimai.nadiaencode.ui.theme.NadiaEncodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NadiaEncodeTheme {
                NadiaEncodeApp()
            }
        }
    }
}
