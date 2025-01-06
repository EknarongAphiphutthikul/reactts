package org.example.springuploadfileapi.controller

import org.example.springuploadfileapi.model.UploadFileRequestModel
import org.example.springuploadfileapi.model.UploadFileResponseModel
import org.example.springuploadfileapi.service.UploadService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController


@RestController
class UploadFileController(
        private val uploadService: UploadService
) : IUploadFileController {

    val logger: Logger = LoggerFactory.getLogger(UploadFileController::class.java)


    override fun uploadFile(
            uploadFileReq: UploadFileRequestModel
    ): ResponseEntity<UploadFileResponseModel> {
        logger.info("uploadFile: $uploadFileReq")
        return ResponseEntity.ok(uploadService.uploadFile(uploadFileReq))
    }
}