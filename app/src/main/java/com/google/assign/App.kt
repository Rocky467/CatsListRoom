package com.google.assign

import android.app.Application
import android.content.res.Resources
import com.google.assign.di.appModule
import com.google.assign.di.dataSourceModule
import com.google.assign.di.repositoryModule
import com.google.assign.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        resource = resources
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataSourceModule, repositoryModule, viewModelModule))
        }
    }

    companion object {
        lateinit var resource: Resources
            private set

        private lateinit var instance: App
        fun get(): App = instance
    }
}