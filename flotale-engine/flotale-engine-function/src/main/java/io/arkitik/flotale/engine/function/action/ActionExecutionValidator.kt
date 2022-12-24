package io.arkitik.flotale.engine.function.action

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:19 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface ActionExecutionValidator {
    fun validateExecution(actionKey: String, elementKey: String, requestedBy: String): Boolean

    interface ExecutorValidatorUnit {
        fun isSupported(actionKey: String, elementKey: String, requestedBy: String): Boolean

        fun canExecute(actionKey: String, elementKey: String, requestedBy: String): Boolean
    }
}
