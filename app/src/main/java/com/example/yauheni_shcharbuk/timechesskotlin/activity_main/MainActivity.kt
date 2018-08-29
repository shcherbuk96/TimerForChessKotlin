package com.example.yauheni_shcharbuk.timechesskotlin.activity_main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.preference.PreferenceManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.yauheni_shcharbuk.timechesskotlin.R
import com.example.yauheni_shcharbuk.timechesskotlin.Settings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView, SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p0 != null && p1 != null) {
            presenter.changeSettings(this, p0, p1)
        }
    }

    override fun setFinish1(time: String) {
        card_first_time_text_view.text = time
        main_first_card_view.setCardBackgroundColor(Color.RED)
        main_first_card_view.isEnabled = false
    }

    override fun setFinish2(time: String) {
        card_second_time_text_view.text = time
        main_second_card_view.setCardBackgroundColor(Color.RED)
        main_second_card_view.isEnabled = false
    }


    override fun setVibrate() {
        val vibe = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibe.vibrate(1000)
    }

    override fun setTime2(time: String) {
        card_second_time_text_view.text = time
    }

    override fun setTime1(time: String) {
        card_first_time_text_view.text = time
    }

    override fun setSettings(time: String, vibrate: Boolean) {
        card_first_time_text_view.text = time
        card_second_time_text_view.text = time
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.registerOnSharedPreferenceChangeListener(this)

        presenter.setSettings(this)

        main_first_card_view.setOnClickListener {
            presenter.startSecondTimer()
            presenter.stopFirstTimer()

            main_first_card_view.setCardBackgroundColor(Color.WHITE)
            main_second_card_view.setCardBackgroundColor(Color.GREEN)
            main_first_card_view.isEnabled = false
            main_second_card_view.isEnabled = true
        }

        main_second_card_view.setOnClickListener {
            presenter.startFirstTimer()
            presenter.stopSecondTimer()

            main_first_card_view.setCardBackgroundColor(Color.GREEN)
            main_second_card_view.setCardBackgroundColor(Color.WHITE)
            main_first_card_view.isEnabled = true
            main_second_card_view.isEnabled = false
        }

        main_stop_image_view.setOnClickListener {
            presenter.stopFirstTimer()
            presenter.stopSecondTimer()
        }

        main_refresh_image_view.setOnClickListener { p0 ->
            presenter.stopFirstTimer()
            presenter.stopSecondTimer()
            presenter.setSettings(p0!!.context)

            main_first_card_view.setCardBackgroundColor(Color.WHITE)
            main_second_card_view.setCardBackgroundColor(Color.WHITE)

            main_first_card_view.isEnabled = true
            main_second_card_view.isEnabled = true
        }

        main_settings_image_view.setOnClickListener { startActivity(Intent(applicationContext, Settings::class.java)) }
    }
}
