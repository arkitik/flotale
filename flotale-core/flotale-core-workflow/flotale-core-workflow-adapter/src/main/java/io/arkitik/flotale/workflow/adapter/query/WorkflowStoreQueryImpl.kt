package io.arkitik.flotale.workflow.adapter.query

import io.arkitik.flotale.workflow.adapter.repository.WorkflowRepository
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class WorkflowStoreQueryImpl(
    private val workflowRepository: WorkflowRepository,
) : StoreQueryImpl<String, WorkflowDomain, FlotaleWorkflow>(workflowRepository), WorkflowStoreQuery {
    override fun existByKeyInAndStatusIn(keys: List<String>, statuses: List<WorkflowStatus>) =
        workflowRepository.existsByWorkflowKeyInAndStatusIn(keys, statuses)

    override fun findByKeyAndNotDeleted(key: String) =
        workflowRepository.findFirstByWorkflowKeyAndStatusNotIn(key, listOf(WorkflowStatus.DELETED))
}
