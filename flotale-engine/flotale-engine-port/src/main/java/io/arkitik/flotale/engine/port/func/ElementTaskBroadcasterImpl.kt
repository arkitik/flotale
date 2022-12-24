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
    override fun elementEnter(taskKey: String, elementKey: String, enteredBy: String) {
        engineBeanStores.map {
            it.elementTaskEnteringBroadcasterUnits(taskKey)
        }.flatten()
            .distinctBy { it.javaClass }
            .filter {
                it.isSupported(taskKey, elementKey, enteredBy)
            }.forEach {
                it.elementEnter(taskKey, elementKey, enteredBy)
            }
    }

    override fun elementExit(taskKey: String, elementKey: String, exitedBy: String) {
        engineBeanStores.map {
            it.elementTaskExitingBroadcasterUnits(taskKey)
        }.flatten()
            .distinctBy { it.javaClass }
            .filter {
                it.isSupported(taskKey, elementKey, exitedBy)
            }.forEach {
                it.elementExit(taskKey, elementKey, exitedBy)
            }
    }
}
