package com.example.yauheni_shcharbuk.timechesskotlin

import android.app.Application
import com.example.yauheni_shcharbuk.timechesskotlin.di.AppComponent
import com.example.yauheni_shcharbuk.timechesskotlin.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.builder()
                .build()
    }
}