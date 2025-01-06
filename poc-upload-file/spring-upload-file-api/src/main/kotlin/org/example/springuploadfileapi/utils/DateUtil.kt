package org.example.springuploadfileapi.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {

    const val yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS"
    const val ZONE_ID = "Asia/Bangkok"

    fun getCurrentTimeInFormat(format: String): String {
        val dateFormat = DateTimeFormatter.ofPattern(format, Locale("th", "TH"))
        return dateFormat.format(Instant.now().atZone(ZoneId.of(ZONE_ID)))
    }

}