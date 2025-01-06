package org.example.springuploadfileapi.utils

import java.io.File

object FileUtil {

    fun createDirectory(filePath: String) {
        File(filePath).mkdirs()
    }

    fun isDirectory(filePath: String): Boolean {
        return isDirectory(File(filePath))
    }

    fun isDirectory(file: File): Boolean {
        return file.isDirectory
    }

    fun isExist(filePath: String): Boolean {
        return isExist(File(filePath))
    }

    fun isExist(file: File): Boolean {
        return file.exists()
    }

    fun getFileNameWithOutExtension(fileName: String): String {
        val endIndex = fileName.lastIndexOf('.')
        return if (endIndex == -1) fileName else fileName.substring(0, endIndex)
    }

    fun getExtension(fileName: String): String {
        val startIndex = fileName.lastIndexOf('.')
        return if (startIndex == -1) "" else fileName.substring(startIndex + 1)
    }

}