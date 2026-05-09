package io.arkitik.flotale.engine.function.dtos

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * @author Ibrahim Al-Tamimi 
 * @since 14:15, Friday, 08/05/2026
 **/
sealed interface FormValidationResult {
    companion object {
        data object Valid : FormValidationResult
        data class Invalid(val errors: List<ErrorResponse>) : FormValidationResult

        fun valid() = Valid
        fun invalid(errors: List<ErrorResponse>) = Invalid(errors)
    }
}