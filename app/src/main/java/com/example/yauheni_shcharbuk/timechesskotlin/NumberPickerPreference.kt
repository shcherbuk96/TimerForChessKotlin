package com.example.yauheni_shcharbuk.timechesskotlin

import android.content.Context
import android.content.res.TypedArray
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.NumberPicker


class NumberPickerPreference(ctxt: Context, attrs: AttributeSet) : DialogPreference(ctxt, attrs) {
    private val minValue: Int = 0
    private val maxValue: Int = 60

    private val wrapSelectorWheel: Boolean = true

    private var picker: NumberPicker? = null
    private var value: Int = 0

    init {
        positiveButtonText = "Установить"
        negativeButtonText = "Отмена"
    }

    override fun onCreateDialogView(): View {
        val lParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lParams.gravity = Gravity.CENTER

        picker = NumberPicker(context)

        if (picker != null) {
            picker?.layoutParams = lParams
        }

        val dialogView = FrameLayout(context)
        dialogView.addView(picker)

        return dialogView
    }

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)

        picker?.minValue = minValue
        picker?.maxValue = maxValue
        picker?.wrapSelectorWheel = wrapSelectorWheel
        picker?.value = getValue()
//        picker?.wrapSelectorWheel=wrapSelectorWheel

    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            picker?.clearFocus()
            val newValue = picker?.value

            if (callChangeListener(newValue)) {
                setValue(newValue)
            }

        }
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any {
        return a.getInt(index, minValue)
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        setValue(if (restorePersistedValue) getPersistedInt(minValue) else defaultValue as Int)
    }

    fun setValue(value: Int?) {
        if (value != null) {
            this.value = value
        }

        persistInt(this.value)
    }

    fun getValue(): Int {
        return this.value
    }
}