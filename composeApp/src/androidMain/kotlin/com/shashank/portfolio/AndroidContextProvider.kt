package com.shashank.portfolio

import android.content.Context

object AndroidContextProvider {
    lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }
}