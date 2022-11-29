package com.lis.testapp2.app

import android.app.Application
import com.lis.testapp2.di.appModule
import com.lis.testapp2.di.dataModule
import com.lis.testapp2.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}