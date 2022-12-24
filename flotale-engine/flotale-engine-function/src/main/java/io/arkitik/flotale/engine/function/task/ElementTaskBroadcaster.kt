package io.arkitik.flotale.engine.function.task

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:26 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface ElementTaskBroadcaster {
    fun elementEnter(taskKey: String, elementKey: String, enteredBy: String)
    fun elementExit(taskKey: String, elementKey: String, exitedBy: String)

    interface EnteringBroadcasterUnit {
        fun isSupported(taskKey: String, elementKey: String, enteredBy: String): Boolean
        fun elementEnter(taskKey: String, elementKey: String, enteredBy: String)
    }

    interface ExitingBroadcasterUnit {
        fun isSupported(taskKey: String, elementKey: String, exitedBy: String): Boolean
        fun elementExit(taskKey: String, elementKey: String, exitedBy: String)
    }
}
