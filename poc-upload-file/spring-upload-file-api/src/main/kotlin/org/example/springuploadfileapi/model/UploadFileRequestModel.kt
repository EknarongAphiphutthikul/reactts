package org.example.springuploadfileapi.model

import org.springframework.web.multipart.MultipartFile

data class UploadFileRequestModel(
    // input by request
    var fileId: String? = null,
    var fileGroupId: String? = null,
    var file: MultipartFile? = null,
    var fileName: String? = null,
    var chunk: Int? = null,
    var totalChunk: Int? = null,

    // variable use by service
    var tempDirGroup: String = "",
)