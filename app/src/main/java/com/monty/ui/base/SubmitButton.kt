package com.monty.ui.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.monty.R
import com.monty.tool.extensions.color
import com.monty.tool.extensions.gone
import com.monty.tool.extensions.visible
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_submit_button.view.*

class SubmitButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var textColorDisabled: Int = 0
    private var textColorEnabled: Int = 0
    private var backgroundColorBase: Int = 0

    var labelTextIdle: String = ""
        set(value) {
            field = value
            submit_form_field_label.text = value
        }

    var labelTextDisabled: String = ""
        set(value) {
            field = value
            submit_form_field_label.text = value
        }

    var buttonState: SubmitButtonState = SubmitButtonState.IDLE
        set(value) {
            field = value
            setState(value)
        }

    var onButtonClickSubject: PublishSubject<SubmitButtonState> = PublishSubject.create()

    var onIdleButtonClickSubject: PublishSubject<Unit> = PublishSubject.create()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_submit_button, this)

        attrs?.let {
            val ta = context.theme.obtainStyledAttributes(it, R.styleable.SubmitFormFieldRounded, 0, 0)
            try {
                textColorDisabled = ta.color(context, R.styleable.SubmitFormFieldRounded_sffr_text_color_disabled,
                    R.color.sffr_text_color_disabled)

                textColorEnabled = ta.color(context, R.styleable.SubmitFormFieldRounded_sffr_text_color_enabled,
                    R.color.sffr_text_color_enabled)

                backgroundColorBase = ta.getResourceId(R.styleable.SubmitFormFieldRounded_sffr_background_color_base,
                    R.drawable.sffr_background_color_base)

                labelTextIdle = ta.getString(R.styleable.SubmitFormFieldRounded_sffr_label_idle) ?: ""
                labelTextDisabled = ta.getString(R.styleable.SubmitFormFieldRounded_sffr_label_disabled) ?: ""
            } finally {
                ta.recycle()
            }
        }

        submit_form_field_root.setOnClickListener {
            onButtonClickSubject.onNext(buttonState)

            if (buttonState == SubmitButtonState.IDLE) {
                onIdleButtonClickSubject.onNext(Unit)
            }
        }

        setState(SubmitButtonState.IDLE)
    }

    private fun setState(value: SubmitButtonState) {
        listOf(submit_form_field_label, submit_form_field_progress, submit_form_field_success).map {
            it.gone()
        }

        when (value) {
            SubmitButtonState.DISABLED -> {
                submit_form_field_label.apply {
                    setTextColor(textColorDisabled)
                    text = labelTextDisabled
                    visible()
                }
                submit_form_field_root.setBackgroundResource(R.drawable.bg_grey_button)
            }
            SubmitButtonState.IDLE -> {
                submit_form_field_label.apply {
                    setTextColor(textColorEnabled)
                    text = labelTextIdle
                    visible()
                }
                submit_form_field_root.setBackgroundResource(backgroundColorBase)
            }
            SubmitButtonState.PROGRESS -> {
                submit_form_field_root.setBackgroundResource(backgroundColorBase)
                submit_form_field_progress.visible()
            }
            SubmitButtonState.SUCCESS -> {
                submit_form_field_root.setBackgroundResource(R.drawable.bg_green_button)
                submit_form_field_success.visible()
            }
        }
    }
}

@ColorInt
fun TypedArray.color(context: Context, index: Int, @ColorRes defValue: Int): Int {
    return this.getColor(index, context.color(defValue))
}
