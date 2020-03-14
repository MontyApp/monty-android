package com.monty.ui.create

import android.content.Context
import com.monty.data.model.response.FileResponse
import com.monty.data.model.ui.Address
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Category
import com.monty.domain.GetCategoriesSingler
import com.monty.domain.advert.AddAdvertCompletabler
import com.monty.domain.advert.GetAdvertObservabler
import com.monty.domain.advert.GetPriceIntervalsSingler
import com.monty.domain.behavior.LoadingCompletableBehavior
import com.monty.domain.file.GetGalleryAttachmentFileSingler
import com.monty.domain.file.UploadPhotoSingler
import com.monty.injection.ApplicationContext
import com.monty.tool.rx.Rx
import com.monty.ui.base.SubmitButtonState
import com.monty.ui.base.SubmitState
import com.monty.ui.create.contract.*
import com.sumera.koreactor.behaviour.completable
import com.sumera.koreactor.behaviour.implementation.LoadingBehaviour
import com.sumera.koreactor.behaviour.messages
import com.sumera.koreactor.behaviour.single
import com.sumera.koreactor.behaviour.triggers
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import com.sumera.koreactor.util.extension.getChange
import com.sumera.koreactor.util.extension.getTrue
import io.reactivex.Observable
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CreateAdvertReactor @Inject constructor(
    private val getGalleryAttachmentFileSingler: GetGalleryAttachmentFileSingler,
    private val getAdvertObservabler: GetAdvertObservabler,
    private val getPriceIntervalsSingler: GetPriceIntervalsSingler,
    private val getCategoriesSingler: GetCategoriesSingler,
    private val addAdvertCompletabler: AddAdvertCompletabler,
    private val uploadPhotoSingler: UploadPhotoSingler,
    private val advertId: String,
    @ApplicationContext val context: Context
) : MviReactor<CreateAdvertState>() {

    override fun createInitialState() = CreateAdvertState.INITIAL

    override fun bind(actions: Observable<MviAction<CreateAdvertState>>) {
        val onTitleChangeAction = actions.ofActionType<OnTitleChangeAction>()
        val onDescriptionChangeAction = actions.ofActionType<OnDescriptionChangeAction>()
        val onPriceChangeAction = actions.ofActionType<OnPriceChangeAction>()
        val onDepositChangeAction = actions.ofActionType<OnDepositChangeAction>()
        val onAddImageClickAction = actions.ofActionType<OnAddImageClickAction>()
        val onGetPhotoFromGalleryActio = actions.ofActionType<OnGetPhotoFromGalleryAction>()
        val onGetPhotoFromCameraAction = actions.ofActionType<OnGetPhotoFromCameraAction>()
        val onUploadPhotoAction = actions.ofActionType<OnUploadPhotoAction>()
        val onSelectedIntervalAction = actions.ofActionType<OnSelectedIntervalAction>()
        val onBackAction = actions.ofActionType<OnBackAction>()
        val onAddAdvertAction = actions.ofActionType<OnAddAdvertAction>()
        val onCategoryClickAction = actions.ofActionType<OnCategoryClickAction>()
        val onSelectCategoryAction = actions.ofActionType<OnSelectCategoryAction>()
        val onDeleteImageClickAction = actions.ofActionType<OnDeleteImageClickAction>()
        val onAddressAction = actions.ofActionType<OnAddressAction>()
        val onMapClickAction = actions.ofActionType<OnMapClickAction>()

        attachLifecycleObservable
            .flatMapSingle { getPriceIntervalsSingler.execute() }
            .map { ChangeIntervalTypesReducer(it) }
            .bindToView()

        attachLifecycleObservable
            .flatMapSingle { getCategoriesSingler.execute() }
            .map { ChangeCategoriesReducer(it) }
            .bindToView()

        if (advertId.isNotEmpty()) {
            attachLifecycleObservable
                .flatMapSingle { getAdvertObservabler.init(advertId).execute().first(Advert.EMPTY) }
                .map { ChangeAdvertReducer(it, context) }
                .bindToView()
        }

        onTitleChangeAction.map { ChangeTitleReducer(it.title) }.bindToView()
        onDescriptionChangeAction.map { ChangeDescriptionReducer(it.description) }.bindToView()
        onSelectCategoryAction.map { ChangeSelectedCategoryReducer(it.category) }.bindToView()
        onAddImageClickAction.map { ShowGetPhotoDialogEvent }.bindToView()
        onGetPhotoFromGalleryActio.map { OpenGalleryEvent }.bindToView()
        onGetPhotoFromCameraAction.map { OpenCameraEvent }.bindToView()
        onCategoryClickAction.map { ShowCategoriesEvent }.bindToView()
        onBackAction.map { BackEvent }.bindToView()
        onMapClickAction.map { SearchMapEvent }.bindToView()
        onAddressAction.map { ChangeAddressReducer(it.address) }.bindToView()

        onDeleteImageClickAction.map { ChangeImageReducer("") }.bindToView()
        onDeleteImageClickAction.map { ChangePhotoStateReducer(SubmitState.IDLE) }.bindToView()
        onDeleteImageClickAction.map { ChangePhotoFileReducer(null) }.bindToView()

        val validTitle = onTitleChangeAction
            .map { it.title.isNotEmpty() }
            .startWith(false)
            .share()

        val validDescription = onDescriptionChangeAction
            .map { it.description.isNotEmpty() }
            .startWith(false)
            .share()

        val validPrice = onPriceChangeAction
            .map { it.price.isNotEmpty() }
            .startWith(false)
            .share()

        val validDeposit = onDepositChangeAction
            .map { it.deposit.isNotEmpty() }
            .startWith(false)
            .share()

        val validCategory = stateObservable.getChange { it.selectedCategory }
            .map { it != Category.EMPTY }
            .startWith(false)
            .share()

        val validImage = stateObservable.getChange { it.image }
            .map { it.isNotEmpty() }
            .startWith(false)
            .share()

        val validAddress = stateObservable.getChange { it.address }
            .map { it != Address.EMPTY }
            .startWith(false)
            .share()

        onPriceChangeAction
            .filter { it.price.isNotEmpty() }
            .map { ChangePriceReducer(it.price.toFloat()) }
            .bindToView()

        onDepositChangeAction
            .filter { it.deposit.isNotEmpty() }
            .map { ChangeDepositReducer(it.deposit.toFloat()) }
            .bindToView()

        onSelectedIntervalAction
            .map { ChangeSelectedIntervalTypeReducer(it.data) }
            .bindToView()

        stateObservable
            .flatMap {
                Rx.observableCombineLatestSevenfold(
                    validTitle,
                    validDescription,
                    validPrice,
                    validDeposit,
                    validCategory,
                    validImage,
                    validAddress
                ).map {
                    val isValid =
                        it.first && it.second && it.third && it.fourth && it.fifth && it.sixth && it.seventh
                    ChangeButtonStateReducer(
                        if (isValid) {
                            SubmitButtonState.IDLE
                        } else {
                            SubmitButtonState.DISABLED
                        }
                    )
                }
            }.bindToView()

        val fileFromUriObservable = onUploadPhotoAction
            .flatMapSingle { getGalleryAttachmentFileSingler.init(it.uri).execute() }

        LoadingBehaviour<File, FileResponse, CreateAdvertState>(
            triggers = triggers(fileFromUriObservable),
            loadWorker = single { uploadPhotoSingler.init(it).execute() },
            cancelPrevious = true,
            loadingMessage = messages(
                { ChangePhotoStateReducer(SubmitState.PROGRESS) },
                { ChangePhotoFileReducer(it) }
            ),
            errorMessage = messages(
                { ChangePhotoStateReducer(SubmitState.ERROR) },
                { ErrorEvent(it.message.toString()) }
            ),
            dataMessage = messages(
                { ChangePhotoStateReducer(SubmitState.SUCCESS) },
                { ChangeImageReducer(it.url) }
            )
        ).bindToView()

        LoadingCompletableBehavior<CreateAdvertState, CreateAdvertState>(
            triggers = triggers(onAddAdvertAction.flatMapSingle { stateSingle }),
            worker = completable {
                addAdvertCompletabler.init(
                    title = it.title,
                    description = it.description,
                    image = it.image,
                    price = it.price,
                    deposit = it.deposit,
                    interval = it.selectedIntervalType?.id ?: "",
                    categoryId = it.selectedCategory.id,
                    advertId = advertId,
                    address = it.address
                ).execute()
            },
            cancelPrevious = true,
            onStart = messages {
                ChangeButtonStateReducer(SubmitButtonState.PROGRESS)
            },
            onError = messages(
                { ChangeButtonStateReducer(SubmitButtonState.IDLE) },
                { ErrorEvent(it.message.toString()) }
            ),
            onComplete = messages {
                ChangeButtonStateReducer(SubmitButtonState.SUCCESS)
            }
        ).bindToView()

        val delaySuccessButtonStateObservable = stateObservable
            .getTrue { it.buttonState == SubmitButtonState.SUCCESS }
            .delay(1000L, TimeUnit.MILLISECONDS)
            .share()

        delaySuccessButtonStateObservable
            .map { SuccessEvent }
            .bindToView()
    }
}
