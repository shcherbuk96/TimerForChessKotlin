package com.example.yauheni_shcharbuk.timechesskotlin.activity_main

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.yauheni_shcharbuk.timechesskotlin.App
import com.example.yauheni_shcharbuk.timechesskotlin.Constants
import com.example.yauheni_shcharbuk.timechesskotlin.NumberPickerPreference
import com.example.yauheni_shcharbuk.timechesskotlin.R
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var model: MainModel

    private var timer1: CountDownTimer? = null
    private var timer2: CountDownTimer? = null

    var seconds1: Long = Constants.DEFAULT_TIME
    var seconds2: Long = Constants.DEFAULT_TIME

    var vibrate: Boolean = true

    init {
        App.appComponent.inject(this)
    }

    fun setSettings(context: Context) {
        val vibrate = model.getVibratePreference(context)
        val time = model.getTimePreference(context)

        seconds1 = time
        seconds2 = time
        this.vibrate = vibrate

        viewState.setSettings(model.timeFormat(time), vibrate)
    }

    fun changeSettings(context: Context, preferences: SharedPreferences, key: String) {
        when (key) {
            context.getString(R.string.key_vibration) ->
                vibrate = preferences.getBoolean(key, true)

            context.getString(R.string.key_time) -> {
                seconds1 = preferences.getInt(key, Constants.DEFAULT_TIME.toInt() / 60).toLong() * 60
                seconds2 = preferences.getInt(key, Constants.DEFAULT_TIME.toInt() / 60).toLong() * 60
            }

//            context.getString(R.string.key_add_time) -> {
//                add_time = p0?.getBoolean(p1, false)!!
//
//                if (add_time) {
//                    change_time = p0.getInt(getString(R.string.key_add_time_change), Constants.DEFAULT_ADD_TIME.toInt()).toLong()
//                }
        }
        viewState.setSettings(model.timeFormat(seconds1), vibrate)
    }

    fun startFirstTimer(interval: Long = 1 * 1000) {
        timer1 = object : CountDownTimer(seconds1 * 1000, interval) {
            override fun onTick(count: Long) {
                seconds1 = count / 1000
                viewState.setTime1(model.timeFormat(seconds1))

                if (vibrate) {
                    if (seconds1.toInt() == 1 * 60) {
                        viewState.setVibrate()
                    }
                }
            }

            override fun onFinish() {
                viewState.setFinish1(model.timeFormat(0))
            }

        }.start()
    }

    fun stopFirstTimer() {
        if (timer1 != null) {
            timer1?.cancel()
        }
    }

    fun startSecondTimer(interval: Long = 1000) {
        timer2 = object : CountDownTimer(seconds2 * 1000, interval) {
            override fun onTick(count: Long) {
                seconds2 = count / 1000
                viewState.setTime2(model.timeFormat(seconds2))

                if (vibrate) {
                    if (seconds2.toInt() == 1 * 60) {
                        viewState.setVibrate()
                    }
                }
            }

            override fun onFinish() {
                viewState.setFinish2(model.timeFormat(0))
            }

        }.start()
    }

    fun stopSecondTimer() {
        if (timer2 != null) {
            timer2?.cancel()
        }
    }
}