package io.arkitik.flotale.workflow.port.jpa

import io.arkitik.flotale.workflow.adapter.WorkflowStoreImpl
import io.arkitik.flotale.workflow.adapter.repository.WorkflowRepository
import io.arkitik.flotale.workflow.store.WorkflowStore
import org.springframework.`data`.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.`annotation`.Bean
import org.springframework.context.`annotation`.Configuration

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.workflow.adapter.repository")
@EntityScan("io.arkitik.flotale.workflow.entity")
class WorkflowJpaPortContext {
    @Bean
    fun workflowStore(workflowRepository: WorkflowRepository): WorkflowStore =
        WorkflowStoreImpl(workflowRepository)
}
