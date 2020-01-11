package com.monty.ui.create.contract

import android.net.Uri
import com.monty.data.model.ui.Category
import com.monty.data.model.ui.SpinnerData
import com.sumera.koreactor.reactor.data.MviAction

sealed class CreateAdvertAction : MviAction<CreateAdvertState>

data class OnTitleChangeAction(val title: String) : CreateAdvertAction()

data class OnDescriptionChangeAction(val description: String) : CreateAdvertAction()

data class OnPriceChangeAction(val price: String) : CreateAdvertAction()

data class OnDepositChangeAction(val deposit: String) : CreateAdvertAction()

data class OnSelectedIntervalAction(val data: SpinnerData) : CreateAdvertAction()

object OnGetPhotoFromGalleryAction : CreateAdvertAction()

object OnGetPhotoFromCameraAction : CreateAdvertAction()

data class OnUploadPhotoAction(val uri: Uri) : CreateAdvertAction()

object OnAddImageClickAction : CreateAdvertAction()

object OnBackAction : CreateAdvertAction()

object OnAddAdvertAction : CreateAdvertAction()

object OnCategoryClickAction : CreateAdvertAction()

data class OnSelectCategoryAction(val category: Category) : CreateAdvertAction()
