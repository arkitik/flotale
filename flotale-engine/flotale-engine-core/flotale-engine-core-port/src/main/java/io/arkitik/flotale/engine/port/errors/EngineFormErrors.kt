package io.arkitik.flotale.engine.port.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * @author Ibrahim Al-Tamimi 
 * @since 14:18, Friday, 08/05/2026
 **/
enum class EngineFormErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    INVALID_FORM_DATA(
        "FLOTALE-ENGINE-FORM-1000",
        "Invalid form data.",
    ),
}
