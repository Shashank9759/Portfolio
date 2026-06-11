package com.shashank.portfolio.util

/**
 * Percent-encodes a string for use in mailto: URL query parameters.
 */
fun encodeUriComponent(input: String): String = buildString {
    input.encodeToByteArray().forEach { byte ->
        val b = byte.toInt() and 0xFF
        when (b) {
            in 'a'.code..'z'.code, in 'A'.code..'Z'.code, in '0'.code..'9'.code,
            '-'.code, '_'.code, '.'.code, '~'.code,
            -> append(b.toChar())
            else -> {
                append('%')
                append((b shr 4).toString(16).uppercase())
                append((b and 0xF).toString(16).uppercase())
            }
        }
    }
}
