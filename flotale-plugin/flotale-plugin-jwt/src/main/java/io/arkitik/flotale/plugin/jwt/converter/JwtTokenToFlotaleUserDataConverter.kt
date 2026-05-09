package io.arkitik.flotale.plugin.jwt.converter

import io.arkitik.flotale.plugin.jwt.errors.WebAuthErrors
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData
import io.arkitik.flotale.protocol.user.converter.FlotaleTokenConverter
import io.arkitik.radix.develop.shared.ext.notAuthorized
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:19, Friday, 08/05/2026
 **/
internal class JwtTokenToFlotaleUserDataConverter(
    private val tokenConverter: FlotaleTokenConverter,
) : Converter<String, FlotaleUserTokenData> {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(JwtTokenToFlotaleUserDataConverter::class.java)
    }

    override fun convert(source: String): FlotaleUserTokenData =
        runCatching {
            tokenConverter.convert(source)
        }.getOrElse {
            LOGGER.error("Error while converting jwt to user data, [Cause by: {}]", it.message, it)
            throw WebAuthErrors.INVALID_TOKEN.notAuthorized()
        }
}