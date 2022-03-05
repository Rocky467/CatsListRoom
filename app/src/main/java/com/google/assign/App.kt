package com.google.assign

import android.app.Application
import android.content.res.Resources
import com.google.assign.di.AppModule.appModule
import com.google.assign.di.DataSourceModule.dataSourceModule
import com.google.assign.di.RepositoryModule.repositoryModule
import com.google.assign.di.ViewModelModule.viewModelModule
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