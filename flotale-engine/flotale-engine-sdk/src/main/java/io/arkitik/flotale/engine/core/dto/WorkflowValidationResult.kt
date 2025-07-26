package io.arkitik.flotale.engine.core.dto

/**
 * Represents the result of validating a workflow's executability
 *
 * @property isValid Whether the workflow is valid and can be executed
 * @property errors List of validation errors if the workflow is invalid
 */
data class WorkflowValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
) {
    companion object {
        /**
         * Factory method for creating a valid result
         */
        fun valid() = WorkflowValidationResult(isValid = true)

        /**
         * Factory method for creating an invalid result with errors
         */
        fun invalid(errors: List<String>) = WorkflowValidationResult(isValid = false, errors = errors)

        /**
         * Factory method for creating an invalid result with a single error
         */
        fun invalid(error: String) = invalid(listOf(error))
    }
}
