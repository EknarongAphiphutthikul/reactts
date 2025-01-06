package org.example.springuploadfileapi.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "upload.file")
data class UploadFileProperties(val tempDir: String)