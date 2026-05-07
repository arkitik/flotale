package io.arkitik.flotale.engine.function.action

import io.arkitik.flotale.engine.function.dtos.ExecuteActionData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:19 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface ActionExecutionValidator {
    fun validateExecution(actionData: ExecuteActionData.Companion.Standard): Boolean

    interface ValidatorUnit {
        fun isSupported(actionData: ExecuteActionData.Companion.Standard): Boolean

        fun canExecute(actionData: ExecuteActionData.Companion.Standard): Boolean
    }
}
