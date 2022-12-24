package io.arkitik.flotale.engine.function.action

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:19 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface ActionExecutor {
    fun executeAction(actionKey: String, elementKey: String, executedBy: String)

    interface ExecutionBroadcasterUnit {
        fun isSupported(actionKey: String, elementKey: String, executedBy: String): Boolean

        fun executeAction(actionKey: String, elementKey: String, executedBy: String)
    }
}
