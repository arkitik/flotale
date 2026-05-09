package io.arkitik.flotale.engine.port

import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.element.sdk.FlotaleElementDomainSdk
import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.FlotaleWorkflowEngine
import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.action.ActionExecutor
import io.arkitik.flotale.engine.function.action.ActionFormProvider
import io.arkitik.flotale.engine.function.task.ElementTaskBroadcaster
import io.arkitik.flotale.engine.operation.core.FlotaleDomainEngineImpl
import io.arkitik.flotale.engine.operation.core.FlotaleWorkflowEngineImpl
import io.arkitik.flotale.engine.port.func.ActionExecutionValidatorImpl
import io.arkitik.flotale.engine.port.func.ActionExecutorImpl
import io.arkitik.flotale.engine.port.func.ActionFormProviderImpl
import io.arkitik.flotale.engine.port.func.ElementTaskBroadcasterImpl
import io.arkitik.flotale.engine.port.func.SpringEngineBeanStore
import io.arkitik.flotale.protocol.transactional.FlotaleTransactionalExecutor
import io.arkitik.flotale.stage.sdk.FlotaleStageDomainSdk
import io.arkitik.flotale.task.sdk.FlotaleTaskDomainSdk
import io.arkitik.flotale.workflow.sdk.FlotaleWorkflowDomainSdk
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 8:21 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@Configuration
class FlotaleEnginePortContext {
    @Bean
    fun flotaleDomainEngine(
        flotaleWorkflowDomainSdk: FlotaleWorkflowDomainSdk,
        flotaleStageDomainSdk: FlotaleStageDomainSdk,
        flotaleTaskDomainSdk: FlotaleTaskDomainSdk,
        flotaleActionDomainSdk: FlotaleActionDomainSdk,
        flotaleTransactionalExecutor: FlotaleTransactionalExecutor,
    ): FlotaleDomainEngine =
        FlotaleDomainEngineImpl(
            flotaleWorkflowDomainSdk = flotaleWorkflowDomainSdk,
            flotaleStageDomainSdk = flotaleStageDomainSdk,
            flotaleTaskDomainSdk = flotaleTaskDomainSdk,
            flotaleActionDomainSdk = flotaleActionDomainSdk,
            flotaleTransactionalExecutor = flotaleTransactionalExecutor,
        )

    @Bean
    fun flotaleWorkflowEngine(
        flotaleWorkflowDomainSdk: FlotaleWorkflowDomainSdk,
        flotaleStageDomainSdk: FlotaleStageDomainSdk,
        flotaleTaskDomainSdk: FlotaleTaskDomainSdk,
        flotaleActionDomainSdk: FlotaleActionDomainSdk,
        flotaleElementDomainSdk: FlotaleElementDomainSdk,

        elementTaskBroadcaster: ElementTaskBroadcaster,
        actionExecutionValidator: ActionExecutionValidator,
        actionExecutor: ActionExecutor,
        actionFormProvider: ActionFormProvider,
        flotaleTransactionalExecutor: FlotaleTransactionalExecutor,
    ): FlotaleWorkflowEngine = FlotaleWorkflowEngineImpl(
        flotaleWorkflowDomainSdk = flotaleWorkflowDomainSdk,
        flotaleStageDomainSdk = flotaleStageDomainSdk,
        flotaleTaskDomainSdk = flotaleTaskDomainSdk,
        flotaleActionDomainSdk = flotaleActionDomainSdk,
        flotaleElementDomainSdk = flotaleElementDomainSdk,
        elementTaskBroadcaster = elementTaskBroadcaster,
        actionExecutionValidator = actionExecutionValidator,
        actionExecutor = actionExecutor,
        actionFormProvider = actionFormProvider,
        flotaleTransactionalExecutor = flotaleTransactionalExecutor,
    )

    @Bean
    fun actionFormProvider(
        engineBeanStores: List<EngineBeanStore>,
    ): ActionFormProvider = ActionFormProviderImpl(engineBeanStores)

    @Bean
    fun actionExecutionBroadcaster(
        engineBeanStores: List<EngineBeanStore>,
    ): ActionExecutor = ActionExecutorImpl(engineBeanStores)

    @Bean
    fun actionExecutionValidator(
        engineBeanStores: List<EngineBeanStore>,
    ): ActionExecutionValidator = ActionExecutionValidatorImpl(engineBeanStores)

    @Bean
    fun elementTaskBroadcaster(
        engineBeanStores: List<EngineBeanStore>,
    ): ElementTaskBroadcaster = ElementTaskBroadcasterImpl(engineBeanStores)

    @Bean
    fun springEngineBeanStore(
        listableBeanFactory: ListableBeanFactory,
    ): EngineBeanStore =
        SpringEngineBeanStore(listableBeanFactory)
}
