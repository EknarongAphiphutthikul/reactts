package org.example.springuploadfileapi.service

import org.example.springuploadfileapi.model.UploadFileRequestModel
import org.example.springuploadfileapi.model.UploadFileResponse
import org.example.springuploadfileapi.properties.UploadFileProperties
import org.example.springuploadfileapi.utils.DateUtil
import org.example.springuploadfileapi.utils.FileUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class UploadService(
        private val uploadFileProperties: UploadFileProperties,
) {

    val logger: Logger = LoggerFactory.getLogger(UploadService::class.java)

    fun uploadFile(request: UploadFileRequestModel): UploadFileResponse {
        generateFileIdAndFileGroupId(request)
        checkTempDirectoryGroup(request)

        val fileNameWithOutExtension = FileUtil.getFileNameWithOutExtension(request.fileName!!)
        val fileExtension = FileUtil.getExtension(request.fileName!!)
        val finalFileName = "${fileNameWithOutExtension}_${request.fileId}.${fileExtension}"

        val finalFullPath = Paths.get(request.tempDirGroup, finalFileName).toString()
        if (request.totalChunk == 1) {
            request.file?.transferTo(File(finalFullPath))
            return UploadFileResponse(request.fileId!!, request.fileGroupId!!)
        }

        request.file?.transferTo(File(Paths.get(request.tempDirGroup, "${finalFileName}.part${request.chunk}").toString()))


        // If all chunks are uploaded, merge the parts
        if (request.chunk == request.totalChunk) {
            mergeChunks(request, finalFileName, request.totalChunk!!)
        }

        logger.info("Upload file success: $finalFullPath chunk: ${request.chunk}")
        return UploadFileResponse(request.fileId!!, request.fileGroupId!!)
    }

    private fun checkTempDirectoryGroup(request: UploadFileRequestModel) {
        request.tempDirGroup = Paths.get(uploadFileProperties.tempDir, request.fileGroupId).toString()
        request.tempDirGroup.takeUnless { FileUtil.isExist(it) }?.let {
            FileUtil.createDirectory(it)
        }
    }

    private fun generateFileIdAndFileGroupId(request: UploadFileRequestModel) {
        request.takeIf { it.fileId.isNullOrBlank() }?.apply {
            this.fileId = UUID.randomUUID().toString()
        }

        request.takeIf { it.fileGroupId.isNullOrBlank() }?.apply {
            val uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
            val fileGroupId = DateUtil.getCurrentTimeInFormat(DateUtil.yyyyMMddHHmmssSSS) + uuid
            this.fileGroupId = fileGroupId
        }
    }

    private fun mergeChunks(request: UploadFileRequestModel, fileName: String, totalChunks: Int) {
        val finalFile = File(request.tempDirGroup, fileName)
        FileOutputStream(finalFile).use { fos ->
            for (i in 1..totalChunks) {
                val chunkFile = File(request.tempDirGroup, "$fileName.part$i")
                Files.copy(chunkFile.toPath(), fos)
                chunkFile.delete() // Delete the chunk after merging
            }
        }
    }

}