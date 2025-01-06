package org.example.springuploadfileapi.controller

import org.example.springuploadfileapi.model.UploadFileRequestModel
import org.example.springuploadfileapi.model.UploadFileResponseModel
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping

interface IUploadFileController {

    @PostMapping(
            "/upload-file",
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadFile(
        uploadFileReq: UploadFileRequestModel
    ): ResponseEntity<UploadFileResponseModel>
}