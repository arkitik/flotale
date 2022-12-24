package io.arkitik.flotale.engine.function

import io.arkitik.flotale.engine.function.action.ActionExecutor
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.task.ElementTaskBroadcaster

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:09 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface EngineBeanStore {
    fun actionExecutionBroadcasterUnits(
        actionKey: String,
    ): List<ActionExecutor.ExecutionBroadcasterUnit>

    fun actionExecutionValidatorUnits(
        actionKey: String,
    ): List<ActionExecutionValidator.ExecutorValidatorUnit>

    fun elementTaskEnteringBroadcasterUnits(
        taskKey: String,
    ): List<ElementTaskBroadcaster.EnteringBroadcasterUnit>

    fun elementTaskExitingBroadcasterUnits(
        taskKey: String,
    ): List<ElementTaskBroadcaster.ExitingBroadcasterUnit>
}
