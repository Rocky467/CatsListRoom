package com.google.assignment.di

import android.app.Application
import com.google.assignment.di.DataBaseModule.databaseModule
import com.google.assignment.di.NetworkModule.networkModule
import com.google.assignment.di.RepositoryModule.repositoryModule
import com.google.assignment.di.ServiceModule.serviceModule
import com.google.assignment.di.ViewModelModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class App : Application() {

    private val modules = arrayListOf(
        databaseModule,
        networkModule,
        repositoryModule,
        serviceModule,
        viewModelModule
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            loadKoinModules(modules)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}