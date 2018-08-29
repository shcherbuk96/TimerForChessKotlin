package com.example.yauheni_shcharbuk.timechesskotlin

import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment

class Settings : PreferenceActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPreferenceFragment()).commit()
    }

    class MyPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preference)

            val sp = preferenceScreen.sharedPreferences
            val time = findPreference(getString(R.string.key_time))

            time.summary = sp.getInt(getString(R.string.key_time), Constants.DEFAULT_TIME.toInt() / 60).toString() + " минут"

            time.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { p0, p1 ->
                time.summary = p1.toString() + " минут"
                true
            }

//            val checkBoxPreference: CheckBoxPreference = findPreference(getString(R.string.key_add_time)) as CheckBoxPreference
//            val numberPickerPreference = findPreference(getString(R.string.key_add_time_change))
//            numberPickerPreference.isEnabled = checkBoxPreference.isChecked
//
//            checkBoxPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//                numberPickerPreference.isEnabled = checkBoxPreference.isChecked
//                false
//            }
        }
    }
}