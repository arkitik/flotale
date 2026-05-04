package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.task.ElementTaskBroadcaster

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:08 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class ElementTaskBroadcasterImpl(
    private val engineBeanStores: List<EngineBeanStore>,
) : ElementTaskBroadcaster {
    override fun elementEnter(elementKey: String, taskKey: String, executedBy: String) {
        engineBeanStores.flatMap {
            it.elementTaskEnteringBroadcasterUnits(taskKey)
        }.filter {
            it.isSupported(elementKey, taskKey, executedBy)
        }.forEach {
            it.elementEnter(elementKey, taskKey, executedBy)
        }
    }

    override fun elementExit(elementKey: String, taskKey: String, executedBy: String) {
        engineBeanStores.flatMap {
            it.elementTaskExitingBroadcasterUnits(taskKey)
        }.filter {
            it.isSupported(elementKey, taskKey, executedBy)
        }.forEach {
            it.elementExit(elementKey, taskKey, executedBy)
        }
    }
}
