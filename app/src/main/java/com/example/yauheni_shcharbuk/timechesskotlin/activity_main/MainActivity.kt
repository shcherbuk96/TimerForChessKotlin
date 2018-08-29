package com.example.yauheni_shcharbuk.timechesskotlin.activity_main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.preference.PreferenceManager
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.yauheni_shcharbuk.timechesskotlin.Constants
import com.example.yauheni_shcharbuk.timechesskotlin.R
import com.example.yauheni_shcharbuk.timechesskotlin.Settings
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : MvpAppCompatActivity(), MainView, SharedPreferences.OnSharedPreferenceChangeListener {
    override fun time(time_first: Int, time_second: Int) {

    }


    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when (p1) {
            getString(R.string.key_vibration) ->
                vibrate = p0?.getBoolean(p1, true)!!

            getString(R.string.key_time) -> {
                secondsRemaining_first = p0?.getInt(p1, Constants.DEFAULT_TIME.toInt())!!.toLong() * 60
                secondsRemaining_second = p0.getInt(p1, Constants.DEFAULT_TIME.toInt()).toLong() * 60
            }

//            getString(R.string.key_add_time) -> {
//                add_time = p0?.getBoolean(p1, false)!!
//
//                if (add_time) {
//                    change_time = p0.getInt(getString(R.string.key_add_time_change), Constants.DEFAULT_ADD_TIME.toInt()).toLong()
//                }
//            }
        }
    }

    override fun onResume() {
        super.onResume()

        card_first_time_text_view.text = time_format(getTimePreference())
        card_second_time_text_view.text = time_format(getTimePreference())
//        change_time = getAddTime()
    }

    var vibrate: Boolean = true
//    var add_time: Boolean = false

    var secondsRemaining_first: Long = Constants.DEFAULT_TIME
    var secondsRemaining_second: Long = Constants.DEFAULT_TIME
//    var change_time: Long = Constants.DEFAULT_ADD_TIME

    var timer1: CountDownTimer? = null
    var timer2: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        vibrate = getVibratePreference()

        secondsRemaining_first = getTimePreference()
        secondsRemaining_second = getTimePreference()

        prefs.registerOnSharedPreferenceChangeListener(this)

        card_first_time_text_view.text = time_format(secondsRemaining_first)
        card_second_time_text_view.text = time_format(secondsRemaining_second)

        main_first_card_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startSecondTimer(secondsRemaining_second)

                if (timer1 != null) {
                    timer1?.cancel()
                }

//                if (add_time) {
//                    secondsRemaining_first += change_time
//                }

                main_first_card_view.setCardBackgroundColor(Color.WHITE)
                main_second_card_view.setCardBackgroundColor(Color.GREEN)
                main_first_card_view.isEnabled = false
                main_second_card_view.isEnabled = true
            }
        })

        main_second_card_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startFirstTimer(secondsRemaining_first)

                if (timer2 != null) {
                    timer2?.cancel()
                }

//                if (add_time) {
//                    secondsRemaining_second += change_time
//                }

                main_first_card_view.setCardBackgroundColor(Color.GREEN)
                main_second_card_view.setCardBackgroundColor(Color.WHITE)
                main_first_card_view.isEnabled = true
                main_second_card_view.isEnabled = false
            }
        })

        main_stop_image_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (timer1 != null) {
                    timer1?.cancel()
                }

                if (timer2 != null) {
                    timer2?.cancel()
                }
            }
        })

        main_refresh_image_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (timer1 != null) {
                    timer1?.cancel()
                }

                if (timer2 != null) {
                    timer2?.cancel()
                }

                secondsRemaining_first = getTimePreference()
                secondsRemaining_second = getTimePreference()

                main_first_card_view.setCardBackgroundColor(Color.WHITE)
                main_second_card_view.setCardBackgroundColor(Color.WHITE)

                main_first_card_view.isEnabled = true
                main_second_card_view.isEnabled = true

                card_first_time_text_view.text = time_format(secondsRemaining_first)
                card_second_time_text_view.text = time_format(secondsRemaining_second)
            }
        })

        main_settings_image_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext, Settings::class.java))
            }
        })
    }


    fun startFirstTimer(time: Long, interval: Long = 1 * 1000) {
        timer1 = object : CountDownTimer(time * 1000, interval) {
            override fun onTick(count: Long) {
                secondsRemaining_first = count / 1000
                card_first_time_text_view.text = time_format(secondsRemaining_first)
                if (vibrate) {
                    if (secondsRemaining_first.toInt() == 1 * 60) {
                        val vibe = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibe.vibrate(1000)
                    }
                }
            }

            override fun onFinish() {
                card_first_time_text_view.text = (secondsRemaining_first - 1).toString()
                main_first_card_view.setCardBackgroundColor(Color.RED)
                main_first_card_view.isEnabled = false
            }

        }.start()
    }

    fun startSecondTimer(time: Long, interval: Long = 1000) {
        timer2 = object : CountDownTimer(time * 1000, interval) {
            override fun onTick(count: Long) {
                secondsRemaining_second = count / 1000
                card_second_time_text_view.text = time_format(secondsRemaining_second)
                if (vibrate) {
                    if (secondsRemaining_second.toInt() == 1 * 60) {
                        val vibe = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibe.vibrate(1000)
                    }
                }
            }

            override fun onFinish() {
                card_second_time_text_view.text = (secondsRemaining_second - 1).toString()
                main_second_card_view.setCardBackgroundColor(Color.RED)
                main_second_card_view.isEnabled = false
            }

        }.start()
    }

    fun time_format(seconds: Long): String {
        return String.format("%02d:%02d",
                TimeUnit.SECONDS.toMinutes(seconds),
                TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)))
    }

    fun getTimePreference(): Long {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        return prefs.getInt(getString(R.string.key_time), Constants.DEFAULT_TIME.toInt() / 60).toLong() * 60
    }

    fun getVibratePreference(): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        return prefs.getBoolean(getString(R.string.key_vibration), true)
    }

    fun getAddTime(): Long {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        return prefs.getInt(getString(R.string.key_add_time_change), Constants.DEFAULT_ADD_TIME.toInt()).toLong()
    }
}
