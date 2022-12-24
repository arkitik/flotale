package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionExecutor
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.action.ActionKey
import io.arkitik.flotale.engine.function.task.ElementTaskBroadcaster
import io.arkitik.flotale.engine.function.task.TaskKey
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.beans.factory.getBeansOfType

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:20 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class SpringEngineBeanStore(
    private val listableBeanFactory: ListableBeanFactory,
) : EngineBeanStore {
    override fun actionExecutionBroadcasterUnits(actionKey: String) =
        listableBeanFactory.getBeansOfType<ActionExecutor.ExecutionBroadcasterUnit>()
            .values
            .filter {
                val actionKeys = it.javaClass.annotations
                    .filterIsInstance<ActionKey>()
                if (actionKeys.isNotEmpty()) {
                    actionKeys
                        .any { actionableBean ->
                            actionableBean.actionKey == actionKey
                        }
                } else true
            }
            .toList()

    override fun actionExecutionValidatorUnits(actionKey: String) =
        listableBeanFactory.getBeansOfType<ActionExecutionValidator.ExecutorValidatorUnit>()
            .values
            .filter {
                val actionKeys = it.javaClass.annotations
                    .filterIsInstance<ActionKey>()
                if (actionKeys.isNotEmpty()) {
                    actionKeys
                        .any { actionableBean ->
                            actionableBean.actionKey == actionKey
                        }
                } else true
            }
            .toList()

    override fun elementTaskEnteringBroadcasterUnits(taskKey: String) =
        listableBeanFactory.getBeansOfType<ElementTaskBroadcaster.EnteringBroadcasterUnit>()
            .values
            .filter {
                val flotaleActionKeys = it.javaClass.annotations
                    .filterIsInstance<TaskKey>()
                if (flotaleActionKeys.isNotEmpty()) {
                    flotaleActionKeys
                        .any { actionableBean ->
                            actionableBean.taskKey == taskKey
                        }
                } else true
            }
            .toList()

    override fun elementTaskExitingBroadcasterUnits(taskKey: String) =
        listableBeanFactory.getBeansOfType<ElementTaskBroadcaster.ExitingBroadcasterUnit>()
            .values
            .filter {
                val flotaleActionKeys = it.javaClass.annotations
                    .filterIsInstance<TaskKey>()
                if (flotaleActionKeys.isNotEmpty()) {
                    flotaleActionKeys
                        .any { actionableBean ->
                            actionableBean.taskKey == taskKey
                        }
                } else true
            }
            .toList()
}
