package com.monty.ui.common.dialog

import android.app.Dialog
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.monty.R
import com.monty.tool.extensions.color
import com.monty.ui.base.BaseDialogFragment
import io.reactivex.subjects.PublishSubject

class DeleteAdvertDialog : BaseDialogFragment() {

    val onPositiveClick: PublishSubject<Unit> = PublishSubject.create<Unit>()

    companion object {
        const val TAG = "DeleteAdvertDialog"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialDialog.Builder(requireContext())
            .title(getString(R.string.delete_advert_title))
            .content(getString(R.string.delete_advert_content))
            .cancelable(true)
            .positiveText(R.string.delete_advert_positivie)
            .negativeText(R.string.delete_advert_negative)
            .positiveColor(requireContext().color(R.color.red))
            .negativeColor(requireContext().color(R.color.blueish_grey))
            .onPositive { _, _ -> onPositiveClick.onNext(Unit) }
            .show()
    }
}
