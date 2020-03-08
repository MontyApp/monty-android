package com.monty.ui.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monty.R
import com.monty.data.model.ui.User
import com.monty.tool.extensions.expandBottomSheet
import com.monty.tool.extensions.visible
import com.monty.ui.base.BaseBottomSheetFragment
import com.thefuntasty.taste.keyboard.TKeyboard
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_contact_dialog.view.*

class ContactDialog : BaseBottomSheetFragment() {

    val onPhoneClick: PublishSubject<Unit> = PublishSubject.create()
    val onEmailClick: PublishSubject<Unit> = PublishSubject.create()

    companion object {
        fun newInstance(): ContactDialog = ContactDialog()
    }

    private var user: User = User.EMPTY

    fun init(user: User) = apply {
        this.user = user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TKeyboard.hide(requireActivity())
        this.expandBottomSheet(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_contact_dialog, container, false)
            .apply {
                contact_name.text = user.name

                contact_phone_click.visible(user.phone.isNotEmpty())

                contact_phone_click.setOnClickListener {
                    dismiss()
                    onPhoneClick.onNext(Unit)
                }

                contact_email_click.setOnClickListener {
                    dismiss()
                    onEmailClick.onNext(Unit)
                }
            }
    }
}
