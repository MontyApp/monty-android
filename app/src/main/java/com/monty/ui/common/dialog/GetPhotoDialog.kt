package com.monty.ui.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monty.R
import com.monty.tool.extensions.expandBottomSheet
import com.monty.ui.base.BaseBottomSheetFragment
import com.thefuntasty.taste.keyboard.TKeyboard
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_get_photo_dialog.view.*

class GetPhotoDialogFragment : BaseBottomSheetFragment() {

    val onCameraClick: PublishSubject<Unit> = PublishSubject.create<Unit>()
    val onGalleryClick: PublishSubject<Unit> = PublishSubject.create<Unit>()

    companion object {
        fun newInstance(): GetPhotoDialogFragment = GetPhotoDialogFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TKeyboard.hide(requireActivity())
        this.expandBottomSheet(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_get_photo_dialog, container, false)
            .apply {
                get_photo_dialog_camera.setOnClickListener {
                    dismiss()
                    onCameraClick.onNext(Unit)
                }
                get_photo_dialog_gallery.setOnClickListener {
                    dismiss()
                    onGalleryClick.onNext(Unit)
                }
            }
    }
}
