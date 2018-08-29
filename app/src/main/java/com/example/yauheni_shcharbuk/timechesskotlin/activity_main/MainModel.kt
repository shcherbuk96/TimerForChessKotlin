package com.example.yauheni_shcharbuk.timechesskotlin.activity_main

import android.content.Context
import android.preference.PreferenceManager
import com.example.yauheni_shcharbuk.timechesskotlin.Constants
import com.example.yauheni_shcharbuk.timechesskotlin.R
import java.util.concurrent.TimeUnit

class MainModel {

    fun timeFormat(seconds: Long): String {
        return String.format("%02d:%02d",
                TimeUnit.SECONDS.toMinutes(seconds),
                TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)))
    }

    fun getTimePreference(context: Context): Long {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(context.getString(R.string.key_time), Constants.DEFAULT_TIME.toInt() / 60).toLong() * 60
    }

    fun getVibratePreference(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(context.getString(R.string.key_vibration), true)
    }

//    fun getAddTime(): Long {
//        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        return prefs.getInt(getString(R.string.key_add_time_change), Constants.DEFAULT_ADD_TIME.toInt()).toLong()
//    }
}