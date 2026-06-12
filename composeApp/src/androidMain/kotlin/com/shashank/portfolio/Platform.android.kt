// composeApp/src/androidMain/kotlin/com/shashank/portfolio/Platform.android.kt
package com.shashank.portfolio

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri

actual fun openUrl(url: String) {
    val context = AndroidContextProvider.appContext
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

actual fun downloadFile(url: String, filename: String) {
    val context = AndroidContextProvider.appContext
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle(filename)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(
            android.os.Environment.DIRECTORY_DOWNLOADS,
            filename
        )
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    downloadManager.enqueue(request)
}

actual fun openEmail(to: String, subject: String, body: String) {
    val context = AndroidContextProvider.appContext

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // Fallback to Gmail web compose if no email app installed
        val gmailUrl = buildString {
            append("https://mail.google.com/mail/?view=cm&fs=1")
            append("&to=").append(Uri.encode(to))
            append("&su=").append(Uri.encode(subject))
            append("&body=").append(Uri.encode(body))
        }
        openUrl(gmailUrl)
    }
}