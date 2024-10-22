package com.longtq.contact_manager

import android.app.Application
import com.longtq.contact_manager.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        instance = this
        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}