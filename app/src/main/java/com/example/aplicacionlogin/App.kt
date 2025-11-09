package com.example.aplicacionlogin

import android.app.Application

class App : Application() {

    private lateinit var floatingManager: ChatFloatingManager

    override fun onCreate() {
        super.onCreate()
        floatingManager = ChatFloatingManager(this)
        floatingManager.register()
    }

    override fun onTerminate() {
        super.onTerminate()
        floatingManager.unregister()
    }
}
