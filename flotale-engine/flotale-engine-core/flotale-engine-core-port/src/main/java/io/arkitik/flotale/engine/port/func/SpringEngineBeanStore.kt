package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.action.ActionExecutor
import io.arkitik.flotale.engine.function.action.ActionFormProvider
import io.arkitik.flotale.engine.function.task.ElementTaskBroadcaster
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
    override fun actionExecutorUnits(actionKey: String): Collection<ActionExecutor.ExecutorUnit> =
        listableBeanFactory.getBeansOfType<ActionExecutor.ExecutorUnit>().values

    override fun actionExecutionValidatorUnits(actionKey: String): Collection<ActionExecutionValidator.ValidatorUnit> =
        listableBeanFactory.getBeansOfType<ActionExecutionValidator.ValidatorUnit>().values

    override fun elementTaskEnteringBroadcasterUnits(taskKey: String): Collection<ElementTaskBroadcaster.EnteringBroadcasterUnit> =
        listableBeanFactory.getBeansOfType<ElementTaskBroadcaster.EnteringBroadcasterUnit>().values

    override fun elementTaskExitingBroadcasterUnits(taskKey: String): Collection<ElementTaskBroadcaster.ExitingBroadcasterUnit> =
        listableBeanFactory.getBeansOfType<ElementTaskBroadcaster.ExitingBroadcasterUnit>().values

    override fun actionFormProviderUnits(actionKey: String): Collection<ActionFormProvider.FormProviderUnit> =
        listableBeanFactory.getBeansOfType<ActionFormProvider.FormProviderUnit>().values
}
