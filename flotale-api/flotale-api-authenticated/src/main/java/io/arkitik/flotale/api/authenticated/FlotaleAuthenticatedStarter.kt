package io.arkitik.flotale.api.authenticated

import io.arkitik.flotale.api.authenticated.dtos.FlotaleUserData
import io.arkitik.flotale.api.authenticated.functions.FlotaleWebMvcConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 10:31 PM, 03/08/2025
 */
@Configuration
class FlotaleAuthenticatedStarter {
    @Bean
    fun flotaleWebMvcConfigurer(
        authorizationTokenConverter: Converter<String, FlotaleUserData>,
    ): WebMvcConfigurer =
        FlotaleWebMvcConfigurer(authorizationTokenConverter)
}