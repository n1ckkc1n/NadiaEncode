package io.github.guziyimai.nadiaencode.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.guziyimai.nadiaencode.ui.theme.NadiaEncodeTheme

@Composable
fun NadiaEncodeSettings(
    currentUrl: String,
    urlList: List<String>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
) {

    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "域名选择",
                    fontSize = 24.sp
                )
            }
        }

        item {
            Column(
                modifier
                    .selectableGroup()
                    .padding(bottom = 16.dp)
            ) {
                urlList.forEach { url ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (url == currentUrl),
                                onClick = { onClick(url) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(20.dp))
                        RadioButton(
                            selected = (url == currentUrl),
                            onClick = null
                        )
                        Text(
                            text = url,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            HorizontalDivider(thickness = 1.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NadiaEncodeSettingsPreview() {
    NadiaEncodeTheme {
        NadiaEncodeSettings(
            currentUrl = "",
            urlList = listOf("1", "2", "3"),
            onClick = {}
        )
    }
}