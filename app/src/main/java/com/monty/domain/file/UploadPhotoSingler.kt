package com.monty.domain.file

import com.monty.data.model.response.FileResponse
import com.monty.domain.base.BaseSingler
import io.reactivex.Single
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UploadPhotoSingler @Inject constructor() : BaseSingler<FileResponse>() {

    private lateinit var image: File

    fun init(image: File) = apply {
        this.image = image
    }

    override fun create(): Single<FileResponse> {
        //val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image)
        //val part = MultipartBody.Part.createFormData("file", image.name, requestFile)
        //return grasonApiManager.uploadFile(part)
        return Single.just(FileResponse("")).delay(2, TimeUnit.SECONDS)
    }
}