package io.arkitik.flotale.plugin.jwt.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:19, Friday, 08/05/2026
 **/
enum class WebAuthErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    EXPIRED_TOKEN("FLOTALE-JWT-1000", "Expired token."),
    INVALID_TOKEN_SIGNATURE("FLOTALE-JWT-2000", "Invalid token signature."),
    INVALID_TOKEN("FLOTALE-JWT-3000", "Invalid token."),
}