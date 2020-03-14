package com.monty.domain.file

import com.monty.data.model.response.FileResponse
import com.monty.data.remote.MontyFirebase
import com.monty.domain.base.BaseSingler
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class UploadPhotoSingler @Inject constructor(
    private val montyFirebase: MontyFirebase
) : BaseSingler<FileResponse>() {

    private lateinit var image: File

    fun init(image: File) = apply {
        this.image = image
    }

    override fun create(): Single<FileResponse> {
        return montyFirebase.uploadFile(image)
            .flatMap { montyFirebase.getFileUrl(it) }
            .map { FileResponse(it) }
    }
}