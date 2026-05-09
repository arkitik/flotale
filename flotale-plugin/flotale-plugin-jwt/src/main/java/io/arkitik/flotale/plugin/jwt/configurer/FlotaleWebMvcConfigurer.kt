package io.arkitik.flotale.plugin.jwt.configurer

import io.arkitik.flotale.plugin.jwt.converter.JwtTokenToFlotaleUserDataConverter
import io.arkitik.flotale.plugin.jwt.resolver.FlotaleUserTokenDataArgumentResolver
import io.arkitik.flotale.protocol.user.converter.FlotaleTokenConverter
import org.springframework.format.FormatterRegistry
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:23, Friday, 08/05/2026
 **/
internal class FlotaleWebMvcConfigurer(
    flotaleTokenConverter: FlotaleTokenConverter,
) : WebMvcConfigurer {
    private val flotaleUserTokenDataArgumentResolver =
        FlotaleUserTokenDataArgumentResolver(flotaleTokenConverter)
    private val jwtTokenToFlotaleUserDataConverter =
        JwtTokenToFlotaleUserDataConverter(flotaleTokenConverter)

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(0, flotaleUserTokenDataArgumentResolver)
    }

    override fun addFormatters(registry: FormatterRegistry) {
        super.addFormatters(registry)
        registry.addConverter(jwtTokenToFlotaleUserDataConverter)
    }
}