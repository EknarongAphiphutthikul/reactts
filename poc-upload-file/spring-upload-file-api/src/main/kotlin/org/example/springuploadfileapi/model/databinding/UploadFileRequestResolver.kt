package org.example.springuploadfileapi.model.databinding

import org.example.springuploadfileapi.model.UploadFileRequestModel
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.core.MethodParameter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest

class UploadFileRequestResolver(
    beanFactory: ConfigurableBeanFactory,
    converters: MutableList<HttpMessageConverter<*>>
): CustomDataBinding(beanFactory, converters), HandlerMethodArgumentResolver {

    companion object {
        const val FILE_ID = "file_id"
        const val FILE_GROUP_ID = "file_group_id"
        const val FILE = "file"
        const val FILE_NAME = "file_name"
        const val CHUNK = "chunk"
        const val TOTAL_CHUNK = "total_chunk"
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType.equals(UploadFileRequestModel::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        webRequest.nativeRequest.let { httpServletRequest ->
            if (httpServletRequest is StandardMultipartHttpServletRequest) {
                val request = mappingRequestFromMultipartParameter(httpServletRequest)

                httpServletRequest.multiFileMap.takeUnless{ it.isEmpty() }?.let {
                    it[FILE]?.first()?.let { file ->
                        request.file = file
                    }
                }

                return request
            }
            return null
        }
    }

    private fun mappingRequestFromMultipartParameter(httpServletRequest: StandardMultipartHttpServletRequest): UploadFileRequestModel {
        val request = UploadFileRequestModel()
        httpServletRequest.parameterMap.takeUnless{ it.isEmpty() }?.map { it.key to it.value.let { values ->
            when(values.size) {
                1 -> values[0]
                else -> values.toList().toString()
            }
        }
        }?.toMap()?.also { mapParam ->
            mapParam.takeIf { it.containsKey(FILE_ID) }?.let {
                request.fileId = it[FILE_ID]
            }
            mapParam.takeIf { it.containsKey(FILE_GROUP_ID) }?.let {
                request.fileGroupId = it[FILE_GROUP_ID]
            }
            mapParam.takeIf { it.containsKey(FILE_NAME) }?.let {
                request.fileName = it[FILE_NAME]
            }
            mapParam.takeIf { it.containsKey(CHUNK) }?.let {
                request.chunk = it[CHUNK]?.toIntOrNull()
            }
            mapParam.takeIf { it.containsKey(TOTAL_CHUNK) }?.let {
                request.totalChunk = it[TOTAL_CHUNK]?.toIntOrNull()
            }
        }
        return request
    }
}