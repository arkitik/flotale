package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.action.ActionExecutor
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
            .filter { broadcasterUnit ->
                val actionKeys = broadcasterUnit.filterByAnnotation<ActionKey>()
                if (actionKeys.isNotEmpty()) {
                    actionKeys.any { actionableBean ->
                        actionableBean.actionKey == actionKey
                    }
                } else true
            }

    override fun actionExecutionValidatorUnits(actionKey: String) =
        listableBeanFactory.getBeansOfType<ActionExecutionValidator.ExecutorValidatorUnit>()
            .values
            .filter { validatorUnit ->
                val actionKeyBeans = validatorUnit.filterByAnnotation<ActionKey>()
                if (actionKeyBeans.isNotEmpty()) {
                    actionKeyBeans.any { bean ->
                        bean.actionKey == actionKey
                    }
                } else true
            }

    override fun elementTaskEnteringBroadcasterUnits(taskKey: String) =
        listableBeanFactory.getBeansOfType<ElementTaskBroadcaster.EnteringBroadcasterUnit>()
            .values
            .filter { enteringBroadcasterUnit ->
                val actionKeyBeans = enteringBroadcasterUnit.filterByAnnotation<TaskKey>()
                if (actionKeyBeans.isNotEmpty()) {
                    actionKeyBeans.any { actionableBean ->
                        actionableBean.taskKey == taskKey
                    }
                } else true
            }

    override fun elementTaskExitingBroadcasterUnits(taskKey: String) =
        listableBeanFactory.getBeansOfType<ElementTaskBroadcaster.ExitingBroadcasterUnit>()
            .values
            .filter { exitingBroadcasterUnit ->
                val actionKeyBeans = exitingBroadcasterUnit.filterByAnnotation<TaskKey>()
                if (actionKeyBeans.isNotEmpty()) {
                    actionKeyBeans.any { actionableBean ->
                        actionableBean.taskKey == taskKey
                    }
                } else true
            }

    private inline fun <reified A : Annotation> Any.filterByAnnotation() = javaClass.annotations.filterIsInstance<A>()
}
