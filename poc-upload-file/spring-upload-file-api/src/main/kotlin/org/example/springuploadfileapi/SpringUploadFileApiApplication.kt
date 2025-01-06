package org.example.springuploadfileapi

import org.example.springuploadfileapi.properties.UploadFileProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
    UploadFileProperties::class,
)
class SpringUploadFileApiApplication

fun main(args: Array<String>) {
    runApplication<SpringUploadFileApiApplication>(*args)
}
