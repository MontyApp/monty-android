package com.monty.domain.file

import android.net.Uri
import com.monty.domain.base.BaseSingler
import com.monty.tool.photo.PhotoAttachmentProcessor
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class GetGalleryAttachmentFileSingler @Inject constructor(
    private val photoAttachmentProcessor: PhotoAttachmentProcessor
) : BaseSingler<File>() {

    private lateinit var uri: Uri

    fun init(uri: Uri): GetGalleryAttachmentFileSingler {
        this.uri = uri
        return this
    }

    override fun create(): Single<File> {
        return Single.fromCallable { photoAttachmentProcessor.getAttachmentFileFromGallery(this.uri) }
    }
}
