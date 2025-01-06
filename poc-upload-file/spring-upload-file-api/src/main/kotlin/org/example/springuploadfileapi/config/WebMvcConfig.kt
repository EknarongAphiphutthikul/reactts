package org.example.springuploadfileapi.config

import org.example.springuploadfileapi.model.databinding.UploadFileRequestResolver
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val beanFactory: ConfigurableBeanFactory,
    private val converters: MutableList<HttpMessageConverter<*>>,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(UploadFileRequestResolver(beanFactory, converters))
    }

}