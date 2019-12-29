package com.monty.ui.create

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.FileProvider
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.monty.R
import com.monty.data.model.ui.IntervalData
import com.monty.data.model.ui.mapper.IntervalMapper
import com.monty.tool.constant.Constant
import com.monty.tool.extensions.gone
import com.monty.tool.extensions.titleTypeface
import com.monty.tool.extensions.visible
import com.monty.tool.helper.FileHelper
import com.monty.tool.intent.Navigation
import com.monty.ui.base.BaseActivity
import com.monty.ui.base.BaseBottomSheetFragment
import com.monty.ui.base.SubmitState
import com.monty.ui.common.dialog.GetPhotoDialogFragment
import com.monty.ui.create.contract.*
import com.squareup.picasso.Picasso
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.data.Optional
import com.sumera.koreactor.util.extension.getChange
import com.sumera.koreactor.util.extension.getNotNull
import com.sumera.koreactor.util.extension.getTrue
import com.thefuntasty.taste.intent.TIntent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_create_advert.*
import javax.inject.Inject

class CreateAdvertActivity : BaseActivity<CreateAdvertState>() {

    private var getPhotoDialogFragment: GetPhotoDialogFragment? = null

    @Inject
    lateinit var reactorFactory: CreateAdvertReactorFactory

    companion object {
        fun getStartIntent(context: Context, advertId: Int? = null): Intent {
            return Intent(context, CreateAdvertActivity::class.java).apply {
                putExtra(Constant.Bundle.ADVERT_ID, advertId)
            }
        }
    }

    private var tempFileUri: Uri? = null

    override fun createReactor(): MviReactor<CreateAdvertState> {
        return getReactor(reactorFactory, CreateAdvertReactor::class.java)
    }

    override val layoutRes: Int = R.layout.activity_create_advert

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create_advert_toolbar_layout.titleTypeface()

        if (savedInstanceState != null) {
            getPhotoDialogFragment = supportFragmentManager
                ?.findFragmentByTag(BaseBottomSheetFragment.TAG) as? GetPhotoDialogFragment
            bindGetPhotoDialogToReactor()
        }

        create_advert_titleEditText.textChanges()
            .map { OnTitleChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_descriptionEditText.textChanges()
            .map { OnDescriptionChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_priceEditText.textChanges()
            .map { OnPriceChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_depositEditText.textChanges()
            .map { OnDepositChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_placeholder.clicks()
            .map { OnAddImageClickAction }
            .bindToReactor()

        create_advert_toolbar.navigationClicks()
            .map { OnBackAction }
            .bindToReactor()

        create_advert_interval.onItemSelected
            .map { OnSelectedIntervalAction(it) }
            .bindToReactor()

        create_advert_button.onIdleButtonClickSubject
            .map { OnAddAdvertAction }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<CreateAdvertState>) {
        stateObservable.getChange { Pair(it.photo, it.photoState) }
            .filter { it.second == SubmitState.SUCCESS }
            .observeState { (photo, _) ->
                photo?.let {
                    Picasso.with(this)
                        .load(photo)
                        .fit()
                        .centerCrop()
                        .into(create_advert_image, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                create_advert_image.visible()
                                create_advert_progress.gone()
                                create_advert_placeholder.gone()
                            }

                            override fun onError() {}
                        })
                }
            }

        stateObservable.getChange { it.intervalTypes }
            .filter { it.isNotEmpty() }
            .observeState {
                create_advert_interval.items = it.map { interval ->
                    IntervalData(
                        id = interval.value,
                        name = IntervalMapper.getInterval(resources, interval.value)
                    )
                }
            }

        stateObservable.getNotNull { Optional(it.selectedIntervalType) }
            .observeState { create_advert_interval.selectedItem = it }

        stateObservable.getChange { it.photoState }
            .observeState { create_advert_progress.visible(it == SubmitState.PROGRESS) }

        stateObservable.getTrue { it.photoState == SubmitState.PROGRESS }
            .observeState { create_advert_placeholder.gone() }

        stateObservable.getTrue { it.photo == null && it.photoState != SubmitState.PROGRESS }
            .observeState { create_advert_placeholder.visible() }

        stateObservable.getChange { it.buttonState }
            .observeState { create_advert_button.buttonState = it }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<CreateAdvertState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                ShowGetPhotoDialogEvent -> showChooseImageSource()
                OpenCameraEvent -> openCamera()
                OpenGalleryEvent -> openGallery()
                BackEvent -> finish()
                SuccessEvent -> finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constant.Intent.TAKE_PHOTO_REQUEST ->
                    tempFileUri?.let { sendAction(OnUploadPhotoAction(it)) }
                Constant.Intent.PICK_IMAGE ->
                    data?.data?.let { sendAction(OnUploadPhotoAction(it)) }
            }
        }
    }

    private fun bindGetPhotoDialogToReactor() {
        getPhotoDialogFragment?.onCameraClick
            ?.map { OnGetPhotoFromCameraAction }
            ?.bindToReactor()

        getPhotoDialogFragment?.onGalleryClick
            ?.map { OnGetPhotoFromGalleryAction }
            ?.bindToReactor()
    }

    private fun openCamera() {
        tempFileUri =
            FileProvider.getUriForFile(this, "$packageName.provider", FileHelper.temp(this))

        tempFileUri?.let {
            startActivityForResult(
                Navigation.getPhotoFromCamera(it),
                Constant.Intent.TAKE_PHOTO_REQUEST
            )
        }
    }

    private fun openGallery() {
        startActivityForResult(TIntent.createLibraryIntent(false), Constant.Intent.PICK_IMAGE)
    }

    private fun showChooseImageSource() {
        supportFragmentManager.let {
            getPhotoDialogFragment = GetPhotoDialogFragment.newInstance()
            getPhotoDialogFragment?.show(it)
            bindGetPhotoDialogToReactor()
        }
    }
}
