package com.example.yauheni_shcharbuk.timechesskotlin.di

import com.example.yauheni_shcharbuk.timechesskotlin.activity_main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModul::class])
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)
}