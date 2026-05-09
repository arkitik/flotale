package io.arkitik.flotale.workflow.port

import io.arkitik.flotale.workflow.operation.FlotaleWorkflowDomainSdkImpl
import io.arkitik.flotale.workflow.sdk.FlotaleWorkflowDomainSdk
import io.arkitik.flotale.workflow.store.WorkflowStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WorkflowPortContext {
    @Bean
    fun flotaleWorkflowDomainSdk(
        workflowStore: WorkflowStore,
    ): FlotaleWorkflowDomainSdk =
        FlotaleWorkflowDomainSdkImpl(workflowStore)
}
