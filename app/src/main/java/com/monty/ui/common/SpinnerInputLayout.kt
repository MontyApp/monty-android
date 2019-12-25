package com.monty.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.monty.R
import com.monty.data.model.ui.SpinnerData
import io.reactivex.subjects.PublishSubject

class SpinnerInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val onItemSelected: PublishSubject<Int> = PublishSubject.create<Int>()

    private var activeArea: View
    private var spinner: Spinner
    private var inputEditText: TextInputEditText
    private var inputLayout: TextInputLayout

    var hint: String = ""
        set(value) {
            if (field != value) {
                field = value
                inputLayout.hint = hint
                invalidate()
            }
        }

    var selectedItem: SpinnerData? = null
        set(value) {
            if (field != value && value != null && items.isNotEmpty()) {
                field = value
                spinner.setSelection(items.indexOf(value))
                inputEditText.setText(value.name)
                invalidate()
            }
        }

    var items: List<SpinnerData> = listOf()
        set(value) {
            if (field != value) {
                field = value
                initAdapter()
                setSpinnerSelectListener()
                setActiveAreaListener()
                invalidate()
            }
        }

    init {
        val rootView = View.inflate(getContext(), R.layout.view_spinner_input_layout, this)
        activeArea = rootView.findViewById(R.id.view_spinner_input_layout_active) as View
        spinner = rootView.findViewById(R.id.view_spinner_input_layout_spinner) as Spinner
        inputEditText = rootView.findViewById(R.id.view_spinner_input_layout_spinner_edittext) as TextInputEditText
        inputLayout = rootView.findViewById(R.id.view_spinner_input_layout_spinner_text) as TextInputLayout

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.SpinnerInputLayout, defStyleAttr, 0)
            try {
                val spinnerHint = ta.getString(R.styleable.SpinnerInputLayout_spinner_hint)
                hint = spinnerHint ?: throw IllegalStateException("Hint not specified")
            } finally {
                ta.recycle()
            }
        }

        initAdapter()
    }

    private fun initAdapter() {
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setActiveAreaListener() {
        activeArea.setOnClickListener {
            spinner.performClick()
        }
    }

    private fun setSpinnerSelectListener() {
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (items.isNotEmpty()) {
                    onItemSelected.onNext(items[position].id)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
    }
}
