package io.arkitik.flotale.workflow.operation.roles

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.radix.develop.operation.OperationRole

/**
 * @author Ibrahim Al-Tamimi 
 * @since 14:36, Monday, 11/05/2026
 **/
internal class WorkflowExistByKeyRole(
    private val workflowStoreQuery: WorkflowStoreQuery,
) : OperationRole<String, Boolean> {
    private val statuses = listOf(WorkflowStatus.ACTIVE)
    override fun String.operateRole(): Boolean {
        return workflowStoreQuery.existByKeyInAndStatusIn(listOf(this), statuses)
    }
}