package io.arkitik.flotale.workflow.operation.roles

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.operation.errors.FlotaleWorkflowErrors
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 11:19 PM, 25/07/2025
 */
internal class WorkflowsDuplicationRole(
    private val workflowStoreQuery: WorkflowStoreQuery,
) : OperationRole<List<String>, Unit> {
    private val statuses = listOf(WorkflowStatus.ACTIVE)

    override fun List<String>.operateRole() {
        if (workflowStoreQuery.existByKeyInAndStatusIn(this, statuses)) {
            throw FlotaleWorkflowErrors.WORKFLOW_ALREADY_EXIST.unprocessableEntity()
        }
    }
}