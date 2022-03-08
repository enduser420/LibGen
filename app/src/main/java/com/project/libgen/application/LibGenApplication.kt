package com.project.libgen.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LibGenApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}