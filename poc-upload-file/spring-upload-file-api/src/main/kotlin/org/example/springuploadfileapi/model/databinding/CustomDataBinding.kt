package org.example.springuploadfileapi.model.databinding

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.core.MethodParameter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

abstract class CustomDataBinding(beanFactory: ConfigurableBeanFactory?, converters: MutableList<HttpMessageConverter<*>>) {

    private val requestHeaderMethodArgumentResolver: CustomRequestHeaderMethodArgumentResolver = CustomRequestHeaderMethodArgumentResolver(beanFactory)
    private val requestParamMethodArgumentResolver: CustomRequestParamMethodArgumentResolver = CustomRequestParamMethodArgumentResolver(beanFactory)
    private val pathVariableMethodArgumentResolver: CustomPathVariableMethodArgumentResolver = CustomPathVariableMethodArgumentResolver()
    private val requestResponseBodyMethodProcessor: CustomRequestResponseBodyMethodProcessor = CustomRequestResponseBodyMethodProcessor(converters)

    open fun resolvePathVariableName(name: String, parameter: MethodParameter, request: NativeWebRequest): Any? {
        return pathVariableMethodArgumentResolver.resolvePathVariableName(name, parameter, request)
    }

    open fun resolveHeaderName(name: String, parameter: MethodParameter, request: NativeWebRequest): Any? {
        return requestHeaderMethodArgumentResolver.resolveHeaderName(name, parameter, request)
    }

    open fun resolveParamName(name: String, parameter: MethodParameter, request: NativeWebRequest): Any? {
        return requestParamMethodArgumentResolver.resolveParamName(name, parameter, request)
    }

    open fun resolveBody(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        return requestResponseBodyMethodProcessor.resolveArgumentName(parameter, mavContainer, webRequest, binderFactory)
    }

    inner class CustomRequestResponseBodyMethodProcessor(converters: MutableList<HttpMessageConverter<*>>) : RequestResponseBodyMethodProcessor(converters) {
        fun resolveArgumentName(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
            return super.resolveArgument(parameter, mavContainer, webRequest, binderFactory)
        }
    }

    inner class CustomPathVariableMethodArgumentResolver: PathVariableMethodArgumentResolver() {
        fun resolvePathVariableName(name: String, parameter: MethodParameter, request: NativeWebRequest): Any? {
            return super.resolveName(name, parameter, request)
        }
    }

    inner class CustomRequestHeaderMethodArgumentResolver(beanFactory: ConfigurableBeanFactory?): RequestHeaderMethodArgumentResolver(beanFactory) {
        fun resolveHeaderName(name: String, parameter: MethodParameter, request: NativeWebRequest): Any? {
            return super.resolveName(name, parameter, request)
        }
    }

    inner class CustomRequestParamMethodArgumentResolver(beanFactory: ConfigurableBeanFactory?): RequestParamMethodArgumentResolver(beanFactory, true) {
        fun resolveParamName(name: String, parameter: MethodParameter, request: NativeWebRequest): Any? {
            return super.resolveName(name, parameter, request)
        }
    }
}