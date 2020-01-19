package com.monty.ui.create

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.FileProvider
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.monty.R
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Category
import com.monty.data.model.ui.IntervalData
import com.monty.data.model.ui.mapper.IntervalMapper
import com.monty.tool.constant.Constant
import com.monty.tool.extensions.gone
import com.monty.tool.extensions.visible
import com.monty.tool.helper.FileHelper
import com.monty.tool.intent.Navigation
import com.monty.ui.base.BaseActivity
import com.monty.ui.base.BaseBottomSheetFragment
import com.monty.ui.base.SubmitState
import com.monty.ui.common.category.CategoriesAdapter
import com.monty.ui.common.category.CategoriesDialogFragment
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

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter
    @Inject
    lateinit var reactorFactory: CreateAdvertReactorFactory
    private var getPhotoDialogFragment: GetPhotoDialogFragment? = null
    private var categoriesDialog: CategoriesDialogFragment? = null

    companion object {
        fun getStartIntent(context: Context, advertId: String? = ""): Intent {
            return Intent(context, CreateAdvertActivity::class.java).apply {
                putExtra(Constant.Bundle.ADVERT_ID, advertId)
            }
        }
    }

    private var tempFileUri: Uri? = null

    override fun createReactor(): MviReactor<CreateAdvertState> {
        return getReactor(reactorFactory, CreateAdvertReactor::class.java)
    }

    override val layoutRes: Int = com.monty.R.layout.activity_create_advert

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            getPhotoDialogFragment = supportFragmentManager
                .findFragmentByTag(BaseBottomSheetFragment.TAG) as? GetPhotoDialogFragment
            bindGetPhotoDialogToReactor()

            categoriesDialog = supportFragmentManager
                .findFragmentByTag(BaseBottomSheetFragment.TAG) as? CategoriesDialogFragment
            bindCategoriesDialogToReactor()
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

        create_advert_category_click_area.clicks()
            .map { OnCategoryClickAction }
            .bindToReactor()

        create_advert_image_delete.clicks()
            .map { OnDeleteImageClickAction }
            .bindToReactor()

        categoriesAdapter.onItemClick
            .map {
                categoriesDialog?.dismiss()
                OnSelectCategoryAction(it)
            }.bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<CreateAdvertState>) {

        stateObservable.getChange { it.advert }
            .filter { it != Advert.EMPTY }
            .observeState {
                create_advert_button.labelTextIdle = getString(R.string.create_advert_edit)
                create_advert_button.labelTextSuccess =
                    getString(R.string.create_advert_edit_success)
            }
        stateObservable.getChange { it.advert }
            .filter { it != Advert.EMPTY }
            .observeState { advert ->
                create_advert_titleEditText.setText(advert.title)
                create_advert_descriptionEditText.setText(advert.description)
                create_advert_priceEditText.setText(advert.price.value.toInt().toString())
                create_advert_depositEditText.setText(advert.deposit.value.toInt().toString())
            }

        stateObservable.getChange { it.image }
            .filter { it.isNotEmpty() }
            .observeState { image ->
                create_advert_image_delete.gone()
                create_advert_image_layout.visible()
                Picasso.with(this)
                    .load(image)
                    .fit()
                    .centerCrop()
                    .into(create_advert_image, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            create_advert_progress.gone()
                            create_advert_image_delete.visible()
                        }

                        override fun onError() {}
                    })
            }

        stateObservable.getChange { it.photoState }
            .filter { it == SubmitState.PROGRESS }
            .observeState { create_advert_progress.visible() }

        stateObservable.getTrue { it.photoState != SubmitState.IDLE }
            .observeState { create_advert_placeholder.gone() }

        stateObservable.getTrue { it.photo == null && it.photoState != SubmitState.PROGRESS && it.image.isEmpty() }
            .observeState {
                create_advert_image_layout.gone()
                create_advert_placeholder.visible()
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

        stateObservable.getChange { it.buttonState }
            .observeState { create_advert_button.buttonState = it }

        stateObservable.getChange { it.selectedCategory }
            .filter { it != Category.EMPTY }
            .observeState {
                create_advert_categoryEditText.setText(
                    it.name,
                    TextView.BufferType.NORMAL
                )
            }

        stateObservable.getChange { Pair(it.categories, it.selectedCategory) }
            .observeState { (categories, selectedCategory) ->
                categoriesAdapter.updateData(categories, selectedCategory)
            }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<CreateAdvertState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                ShowGetPhotoDialogEvent -> showChooseImageSource()
                OpenCameraEvent -> openCamera()
                OpenGalleryEvent -> openGallery()
                BackEvent -> finish()
                SuccessEvent -> finish()
                ShowCategoriesEvent -> showCategoriesDialog()
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

    private fun bindCategoriesDialogToReactor() {
        categoriesDialog?.setAdapter(categoriesAdapter)
    }

    private fun showCategoriesDialog() {
        categoriesDialog = CategoriesDialogFragment()
        categoriesDialog?.show(supportFragmentManager)
        bindCategoriesDialogToReactor()
    }
}
