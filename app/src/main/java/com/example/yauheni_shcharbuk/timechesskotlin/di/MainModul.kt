package com.example.yauheni_shcharbuk.timechesskotlin.di

import com.example.yauheni_shcharbuk.timechesskotlin.activity_main.MainModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModul {
    @Provides
    @Singleton
    fun mainModel(): MainModel = MainModel()
}