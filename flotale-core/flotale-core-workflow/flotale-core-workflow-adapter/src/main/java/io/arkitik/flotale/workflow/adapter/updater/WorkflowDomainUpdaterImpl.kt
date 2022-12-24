package io.arkitik.flotale.workflow.adapter.updater

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.flotale.workflow.store.updater.WorkflowDomainUpdater

internal class WorkflowDomainUpdaterImpl(
    private val entity: FlotaleWorkflow,
) : WorkflowDomainUpdater {
    override fun WorkflowStatus.status(): WorkflowDomainUpdater {
        entity.status = this
        return this@WorkflowDomainUpdaterImpl
    }

    override fun update(): FlotaleWorkflow = entity
}
