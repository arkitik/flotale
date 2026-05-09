package io.arkitik.flotale.plugin.jwt.resolver

import io.arkitik.flotale.protocol.user.FlotaleUserTokenData
import io.arkitik.flotale.protocol.user.converter.FlotaleTokenConverter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:19, Friday, 08/05/2026
 **/
internal class FlotaleUserTokenDataArgumentResolver(
    private val converter: FlotaleTokenConverter,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        FlotaleUserTokenData::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): FlotaleUserTokenData {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        val authorization = request?.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authorization.isNullOrBlank()) FlotaleUserTokenData.system
        else converter.convert(authorization)
    }
}