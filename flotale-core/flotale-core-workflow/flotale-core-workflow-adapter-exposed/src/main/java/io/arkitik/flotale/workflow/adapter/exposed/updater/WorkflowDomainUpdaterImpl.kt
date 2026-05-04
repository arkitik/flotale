package io.arkitik.flotale.workflow.adapter.exposed.updater

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowExposed
import io.arkitik.flotale.workflow.store.updater.WorkflowDomainUpdater

internal class WorkflowDomainUpdaterImpl(
    private val entity: FlotaleWorkflowExposed,
) : WorkflowDomainUpdater {

    override fun WorkflowStatus.status(): WorkflowDomainUpdater {
        entity.status = this
        return this@WorkflowDomainUpdaterImpl
    }

    override fun update(): WorkflowDomain = entity
}
