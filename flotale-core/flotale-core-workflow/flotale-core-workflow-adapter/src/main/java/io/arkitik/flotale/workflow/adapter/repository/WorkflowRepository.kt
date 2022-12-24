package io.arkitik.flotale.workflow.adapter.repository

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface WorkflowRepository : RadixRepository<String, FlotaleWorkflow> {
    fun existsByWorkflowKeyAndStatusIn(
        actionKey: String,
        statuses: List<WorkflowStatus>,
    ): Boolean

    fun findFirstByWorkflowKeyAndStatusNotIn(
        actionKey: String,
        statuses: List<WorkflowStatus>,
    ): FlotaleWorkflow?
}
