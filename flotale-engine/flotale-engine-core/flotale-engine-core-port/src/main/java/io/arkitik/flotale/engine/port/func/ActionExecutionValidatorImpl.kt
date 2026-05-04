package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:16 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class ActionExecutionValidatorImpl(
    private val engineBeanStores: List<EngineBeanStore>,
) : ActionExecutionValidator {
    override fun validateExecution(actionKey: String, elementKey: String, requestedBy: String): Boolean {
        return engineBeanStores.flatMap { validatorUnit ->
            validatorUnit.actionExecutionValidatorUnits(actionKey)
        }.filter { validatorUnit ->
            validatorUnit.isSupported(actionKey, elementKey, requestedBy)
        }.all { validatorUnit ->
            validatorUnit.canExecute(actionKey, elementKey, requestedBy)
        }
    }
}
