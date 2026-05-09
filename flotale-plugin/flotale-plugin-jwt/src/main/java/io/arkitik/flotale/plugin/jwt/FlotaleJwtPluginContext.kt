package io.arkitik.flotale.plugin.jwt

import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.plugin.jwt.config.FlotaleJwtProperties
import io.arkitik.flotale.plugin.jwt.configurer.FlotaleWebMvcConfigurer
import io.arkitik.flotale.plugin.jwt.converter.JwtFlotaleTokenConverter
import io.arkitik.flotale.plugin.jwt.converter.JwtTokenToFlotaleUserDataConverter
import io.arkitik.flotale.plugin.jwt.resolver.FlotaleUserTokenDataArgumentResolver
import io.arkitik.flotale.plugin.jwt.validator.JwtSystemActionExecutionValidatorUnit
import io.arkitik.flotale.plugin.jwt.validator.JwtUserActionExecutionValidatorUnit
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData
import io.arkitik.flotale.protocol.user.converter.FlotaleTokenConverter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:19, Friday, 08/05/2026
 **/
@Configuration
@EnableConfigurationProperties(FlotaleJwtProperties::class)
class FlotaleJwtPluginContext {

    @Bean
    @ConditionalOnMissingBean
    fun jwtFlotaleTokenConverter(
        properties: FlotaleJwtProperties,
    ): FlotaleTokenConverter =
        JwtFlotaleTokenConverter(properties)

    @Bean
    fun flotaleUserTokenDataArgumentResolver(
        flotaleTokenConverter: FlotaleTokenConverter,
    ): HandlerMethodArgumentResolver =
        FlotaleUserTokenDataArgumentResolver(flotaleTokenConverter)

    @Bean
    fun jwtTokenToFlotaleUserDataConverter(
        flotaleTokenConverter: FlotaleTokenConverter,
    ): Converter<String, FlotaleUserTokenData> =
        JwtTokenToFlotaleUserDataConverter(flotaleTokenConverter)

    @Bean
    fun flotaleJwtWebMvcConfigurer(
        flotaleTokenConverter: FlotaleTokenConverter,
    ): WebMvcConfigurer =
        FlotaleWebMvcConfigurer(flotaleTokenConverter)

    @Bean
    @ConditionalOnBooleanProperty("flotale.jwt.default-validator", havingValue = true, matchIfMissing = true)
    fun jwtActionExecutionValidatorUnit(): ActionExecutionValidator.ValidatorUnit =
        JwtUserActionExecutionValidatorUnit()

    @Bean
    @ConditionalOnBooleanProperty("flotale.jwt.default-validator", havingValue = true, matchIfMissing = true)
    fun jwtSystemActionExecutionValidatorUnit(
        flotaleJwtProperties: FlotaleJwtProperties,
    ): ActionExecutionValidator.ValidatorUnit =
        JwtSystemActionExecutionValidatorUnit(flotaleJwtProperties)
}
