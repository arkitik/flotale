package io.arkitik.flotale.workflow.port.exposed

import io.arkitik.flotale.workflow.adapter.exposed.ExposedWorkflowStore
import io.arkitik.flotale.workflow.store.WorkflowStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WorkflowExposedPortContext {

    @Bean
    fun workflowStore(
        @Autowired(required = false) database: Database?,
    ): WorkflowStore = ExposedWorkflowStore(database)
}
