package com.google.assignment.di

import android.app.Application
import android.content.res.Resources
import com.google.assignment.di.DataBaseModule.databaseModule
import com.google.assignment.di.DataSourceModule.dataSourceModule
import com.google.assignment.di.NetworkModule.networkModule
import com.google.assignment.di.RepositoryModule.repositoryModule
import com.google.assignment.di.ServiceModule.serviceModule
import com.google.assignment.di.ViewModelModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var resource: Resources
    }

    override fun onCreate() {
        super.onCreate()
        resource = resources
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                serviceModule,
                dataSourceModule,
                repositoryModule,
                databaseModule,
                viewModelModule
            )
        }
    }

}