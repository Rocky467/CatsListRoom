package com.google.assign.di

import android.app.Application
import android.content.res.Resources
import com.google.assign.di.ApplicationModule.applicationModule
import com.google.assign.di.DataSourceModule.dataSourceModule
import com.google.assign.di.RepositoryModule.repositoryModule
import com.google.assign.di.ServiceModule.serviceModule
import com.google.assign.di.ViewModelModule.viewModelModule
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
                applicationModule,
                serviceModule,
                dataSourceModule,
                repositoryModule,
                viewModelModule
            )
        }
    }

}