package com.example.yauheni_shcharbuk.timechesskotlin.activity_main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun setSettings(time: String, vibrate: Boolean)

    fun setTime1(time: String)

    fun setTime2(time: String)

    fun setVibrate()

    fun setFinish1(time: String)

    fun setFinish2(time: String)
}