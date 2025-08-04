package io.arkitik.flotale.api.authenticated.functions

import io.arkitik.flotale.api.authenticated.dtos.FlotaleUserData
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 10:32 PM, 03/08/2025
 */
internal class FlotaleWebMvcConfigurer(
    private val authorizationTokenConverter: Converter<String, FlotaleUserData>,
) : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        super.addFormatters(registry)
        registry.addConverter(authorizationTokenConverter)
    }
}