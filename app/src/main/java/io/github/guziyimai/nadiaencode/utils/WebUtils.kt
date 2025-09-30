package io.github.guziyimai.nadiaencode.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri


object WebUtils {
    fun openLinkInBrowser(ctx: Context, url: String) { // 要打开的链接地址
        val intent = Intent(
            Intent.ACTION_VIEW, url.toUri()
        )
        ctx.startActivity(intent)
    }
}