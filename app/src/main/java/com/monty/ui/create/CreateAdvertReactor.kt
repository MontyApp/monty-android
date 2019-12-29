package com.monty.ui.create

import com.monty.data.model.response.FileResponse
import com.monty.data.model.ui.Address
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Interval
import com.monty.data.model.ui.Price
import com.monty.domain.advert.AddAdvertCompletabler
import com.monty.domain.advert.GetPriceIntervalsSingler
import com.monty.domain.behavior.LoadingCompletableBehavior
import com.monty.domain.file.GetGalleryAttachmentFileSingler
import com.monty.domain.file.UploadPhotoSingler
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
import com.sumera.koreactor.util.extension.getTrue
import io.reactivex.Observable
import org.threeten.bp.LocalDateTime
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CreateAdvertReactor @Inject constructor(
    private val getGalleryAttachmentFileSingler: GetGalleryAttachmentFileSingler,
    private val getPriceIntervalsSingler: GetPriceIntervalsSingler,
    private val addAdvertCompletabler: AddAdvertCompletabler,
    private val uploadPhotoSingler: UploadPhotoSingler
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

        onTitleChangeAction.map { ChangeTitleReducer(it.title) }.bindToView()
        onDescriptionChangeAction.map { ChangeDescriptionReducer(it.description) }.bindToView()
        onAddImageClickAction.map { ShowGetPhotoDialogEvent }.bindToView()
        onGetPhotoFromGalleryActio.map { OpenGalleryEvent }.bindToView()
        onGetPhotoFromCameraAction.map { OpenCameraEvent }.bindToView()
        onBackAction.map { BackEvent }.bindToView()

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

        attachLifecycleObservable
            .flatMapSingle { getPriceIntervalsSingler.execute() }
            .map { ChangeIntervalTypesReducer(it) }
            .bindToView()

        stateObservable
            .flatMap {
                Rx.observableCombineLatestTriple(
                    validTitle,
                    validDescription,
                    validPrice,
                    validDeposit
                ).map {
                    val isValid = it.first && it.second && it.third && it.fourth
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
                { ChangePhotoUuidReducer(it.uuid) }
            )
        ).bindToView()

        LoadingCompletableBehavior<CreateAdvertState, CreateAdvertState>(
            triggers = triggers(onAddAdvertAction.flatMapSingle { stateSingle }),
            worker = completable {
                addAdvertCompletabler.init(
                    Advert(
                        id = LocalDateTime.now().nano,
                        title = it.title,
                        image = "https://i.picsum.photos/500/300.jpg",
                        description = it.description,
                        createdAt = LocalDateTime.now(),
                        price = Price.EMPTY.copy(
                            value = it.price,
                            interval = Interval(it.selectedIntervalType?.id ?: "")
                        ),
                        deposit = Price.EMPTY.copy(value = it.deposit),
                        address = Address.EMPTY,
                        isFavourite = false
                    )
                ).execute()
            },
            cancelPrevious = true,
            onStart = messages {
                ChangeButtonStateReducer(SubmitButtonState.PROGRESS)
            },
            onError = messages {
                ChangeButtonStateReducer(SubmitButtonState.IDLE)
            },
            onComplete = messages {
                ChangeButtonStateReducer(SubmitButtonState.SUCCESS)
            }
        ).bindToView()

        val delaySuccessButtonStateObservable = stateObservable
            .getTrue { it.buttonState == SubmitButtonState.SUCCESS }
            .delay(300L, TimeUnit.MILLISECONDS)
            .share()

        delaySuccessButtonStateObservable
            .map { SuccessEvent }
            .bindToView()
    }
}
