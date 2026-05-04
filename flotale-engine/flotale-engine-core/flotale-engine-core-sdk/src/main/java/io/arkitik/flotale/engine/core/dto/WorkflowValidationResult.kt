package io.arkitik.flotale.engine.core.dto


/**
 * Represents the result of a workflow validation process.
 * This sealed interface can either indicate a valid workflow or highlight issues in validation.
 */
sealed interface WorkflowValidationResult {
    /**
     * Companion object providing predefined validation results and methods to create instances
     * of `WorkflowValidationResult` including `Valid` and `Invalid` states.
     */
    companion object {

        /**
         * Represents a valid result in the process of workflow validation.
         *
         * This object is used to signify that a given workflow has been successfully
         * validated without any errors or issues. It implements the `WorkflowValidationResult`
         * interface as a type of validation result.
         */
        object Valid : WorkflowValidationResult

        /**
         * Represents an invalid result from workflow validation.
         *
         * This data class is a specific implementation of the `WorkflowValidationResult` sealed interface,
         * used to indicate that a validation process for a workflow has failed. The failure details are encapsulated
         * within the `errors` property, which contains a list of `InvalidReason` instances. Each `InvalidReason`
         * provides details about the specific validation failure, including a key and a descriptive reason.
         *
         * @property errors A list of `InvalidReason` instances describing each of the validation errors.
         */
        data class Invalid(
            val errors: List<InvalidReason>,
        ) : WorkflowValidationResult

        /**
         * Represents an invalidation reason with a key and its associated descriptive reason.
         *
         * This class is utilized within the workflow validation process to define specific reasons
         * for invalid workflows or elements. It includes a `key` that identifies the invalidation
         * category or field, and a `reason` that provides a detailed descriptive explanation.
         *
         * Instances of this class are typically used as part of the `Invalid` result within the
         * `WorkflowValidationResult` sealed interface.
         */
        data class InvalidReason(
            val key: String,
            val reason: String,
        )

        /**
         * Returns an instance of `Valid` representing a successful workflow validation result.
         *
         * This method is used to indicate that a workflow validation has been successful
         * without any errors or issues.
         *
         * @return An instance of `WorkflowValidationResult.Valid`.
         */
        fun valid() = Valid

        /**
         * Creates an `Invalid` instance of `WorkflowValidationResult` with the provided list of errors.
         *
         * @param errors A list of `InvalidReason` objects representing the reasons for workflow validation failure.
         * @return `Invalid` instance encapsulating the provided error reasons.
         */
        fun invalid(errors: List<InvalidReason>) = Invalid(errors)

        /**
         * Marks the workflow validation result as invalid for a specific reason.
         *
         * @param reason The reason for marking the workflow as invalid.
         */
        fun invalid(reason: InvalidReason) = invalid(listOf(reason))

        /**
         * Creates a validation result for an invalid workflow.
         *
         * @param key The key identifying the invalid item.
         * @param reason The reason describing why the item is invalid.
         */
        fun invalid(key: String, reason: String) = invalid(InvalidReason(key, reason))
    }
}
