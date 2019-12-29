package com.monty.ui.create

import com.monty.data.model.response.FileResponse
import com.monty.domain.file.GetGalleryAttachmentFileSingler
import com.monty.domain.file.UploadPhotoSingler
import com.monty.ui.base.SubmitState
import com.monty.ui.create.contract.*
import com.sumera.koreactor.behaviour.implementation.LoadingBehaviour
import com.sumera.koreactor.behaviour.messages
import com.sumera.koreactor.behaviour.single
import com.sumera.koreactor.behaviour.triggers
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class CreateAdvertReactor @Inject constructor(
    private val getGalleryAttachmentFileSingler: GetGalleryAttachmentFileSingler,
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
        val onBackAction = actions.ofActionType<OnBackAction>()

        onTitleChangeAction.map { ChangeTitleReducer(it.title) }.bindToView()
        onDescriptionChangeAction.map { ChangeDescriptionReducer(it.description) }.bindToView()
        onAddImageClickAction.map { ShowGetPhotoDialogEvent }.bindToView()
        onGetPhotoFromGalleryActio.map { OpenGalleryEvent }.bindToView()
        onGetPhotoFromCameraAction.map { OpenCameraEvent }.bindToView()
        onBackAction.map { BackEvent }.bindToView()

        onPriceChangeAction
            .filter { it.price.isNotEmpty() }
            .map { ChangePriceReducer(it.price.toFloat()) }
            .bindToView()

        onDepositChangeAction
            .filter { it.deposit.isNotEmpty() }
            .map { ChangeDepositReducer(it.deposit.toFloat()) }
            .bindToView()

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
    }
}
