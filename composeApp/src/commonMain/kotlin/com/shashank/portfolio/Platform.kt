package com.shashank.portfolio

/**
 * Platform-specific utilities for opening URLs and downloading files.
 * Implemented per-target in wasmJsMain.
 */
expect fun openUrl(url: String)

expect fun downloadFile(url: String, filename: String)

/** Opens the user's default email client with pre-filled recipient, subject, and body. */
expect fun openEmail(to: String, subject: String, body: String)
