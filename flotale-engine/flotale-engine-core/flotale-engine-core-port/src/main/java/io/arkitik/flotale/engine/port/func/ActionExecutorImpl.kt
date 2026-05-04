package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionExecutor

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:15 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class ActionExecutorImpl(
    private val engineBeanStores: List<EngineBeanStore>,
) : ActionExecutor {
    override fun executeAction(actionKey: String, elementKey: String, elementType: String, executedBy: String) {
        engineBeanStores.flatMap { it.actionExecutorUnits(actionKey) }
            .filter { it.isSupported(actionKey, elementKey, elementType, executedBy) }
            .forEach { it.executeAction(actionKey, elementKey, elementType, executedBy) }
    }
}
