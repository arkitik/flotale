package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionExecutor
import io.arkitik.flotale.engine.function.dtos.ExecuteActionData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:15 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class ActionExecutorImpl(
    private val engineBeanStores: List<EngineBeanStore>,
) : ActionExecutor {
    override fun executeAction(actionData: ExecuteActionData) {
        engineBeanStores.flatMap { it.actionExecutorUnits(actionData.actionKey) }
            .filter { it.isSupported(actionData) }
            .forEach { it.executeAction(actionData) }
    }
}
