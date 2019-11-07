package com.monty.ui.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import io.reactivex.subjects.PublishSubject

abstract class BaseDialogFragment : DialogFragment() {

    open val positiveClick = PublishSubject.create<Unit>()
    open val negativeClick = PublishSubject.create<Unit>()

    companion object {
        const val TAG = "dialog_fragment"
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }
}
