package io.github.guziyimai.nadiaencode.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.kevinnzou.web.AccompanistWebViewClient
import com.kevinnzou.web.LoadingState
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberSaveableWebViewState
import com.kevinnzou.web.rememberWebViewNavigator
import io.github.guziyimai.nadiaencode.NadiaEncodeApplication.Companion.USER_AGENT
import io.github.guziyimai.nadiaencode.ui.theme.NadiaEncodeTheme
import io.github.guziyimai.nadiaencode.utils.WebUtils

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NadiaEncodeSots(
    url: String,
    modifier: Modifier = Modifier
) {
    val url = "${url}/sots.html"

    val webViewState = rememberSaveableWebViewState()

    val loadingState = remember { webViewState.loadingState }

    val navigator = rememberWebViewNavigator()

    val ctx = LocalContext.current
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(navigator, refreshing) {
        val bundle = webViewState.viewState
        if (bundle == null) {
            navigator.loadUrl(url, mapOf("User-Agent" to USER_AGENT))
        }
        if (refreshing) {
            navigator.reload()
        }
    }

    PullToRefreshBox(
        onRefresh = { refreshing = true },
        isRefreshing = refreshing,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {

                if (loadingState is LoadingState.Loading) {
                    CircularProgressIndicator(
                        progress = { loadingState.progress },
                    )
                }

                WebView(
                    state = webViewState,
                    onCreated = { webView ->
                        webView.clipToOutline = true
                        webView.setBackgroundColor(Color.Transparent.toArgb())
                        webView.settings.javaScriptEnabled = true
                    },
                    modifier = Modifier.fillMaxSize(),
                    navigator = navigator,
                    client = object : AccompanistWebViewClient() {
                        override fun onPageFinished(
                            view: WebView,
                            url: String?
                        ) {
                            refreshing = false
                            super.onPageFinished(view, url)
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            Log.d("requestUrl", request?.url.toString())
                            WebUtils.openLinkInBrowser(ctx, request?.url.toString())
                            return true
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun NadiaEncodeSotsPreview() {
    NadiaEncodeTheme {
        NadiaEncodeSots(
            url = ""
        )
    }
}
