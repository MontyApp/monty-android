package com.monty.ui.base

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.monty.R

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "bottom_sheet_dialog_fragment"
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }
}
