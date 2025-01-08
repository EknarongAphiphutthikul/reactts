package org.example.springuploadfileapi.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UploadFileResponseModel(
    val status: BaseStatus,
    val data: UploadFileResponse,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class BaseStatus(
    val code: String,
    val message: String,
    val description: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UploadFileResponse(
    val fileId: String,
    val fileGroupId: String
)