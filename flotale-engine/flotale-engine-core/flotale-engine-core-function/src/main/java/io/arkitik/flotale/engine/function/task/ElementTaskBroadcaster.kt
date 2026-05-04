package io.arkitik.flotale.engine.function.task

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:26 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface ElementTaskBroadcaster {
    fun elementEnter(elementKey: String, elementType: String, taskKey: String, executedBy: String)
    fun elementExit(elementKey: String, elementType: String, taskKey: String, executedBy: String)

    interface EnteringBroadcasterUnit {
        fun isSupported(elementKey: String, elementType: String, taskKey: String, executedBy: String): Boolean
        fun elementEnter(elementKey: String, elementType: String, taskKey: String, executedBy: String)
    }

    interface ExitingBroadcasterUnit {
        fun isSupported(elementKey: String, elementType: String, taskKey: String, executedBy: String): Boolean
        fun elementExit(elementKey: String, elementType: String, taskKey: String, executedBy: String)
    }
}
