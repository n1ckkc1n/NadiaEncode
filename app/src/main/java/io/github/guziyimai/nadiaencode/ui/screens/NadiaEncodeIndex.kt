package io.github.guziyimai.nadiaencode.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NadiaEncodeIndex(
    url:String,
    modifier: Modifier = Modifier
) {
    val url = "file:///android_asset/NadiaEncodeOffline/index.html"

    val webViewState = rememberSaveableWebViewState()

    val loadingState = webViewState.loadingState

    val navigator = rememberWebViewNavigator()

    val ctx = LocalContext.current
    val webClient = remember { IndexWebClient(ctx) }

    LaunchedEffect(navigator) {
        val bundle = webViewState.viewState

        if (bundle == null) {
            navigator.loadUrl(url, mapOf("User-Agent" to USER_AGENT))
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
                webView.setInitialScale(100)
                webView.settings.javaScriptEnabled = true
            },
            modifier = Modifier.fillMaxSize(),
            navigator = navigator,
            client = webClient
        )
    }
}

class IndexWebClient(private val ctx: Context): AccompanistWebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        Log.d("requestUrl", request?.url.toString())
        WebUtils.openLinkInBrowser(ctx,request?.url.toString())
        return true
    }
}

@Preview
@Composable
fun NadiaEncodeIndexPreview() {
    NadiaEncodeTheme {

    }
}
