package io.arkitik.flotale.workflow.operation.roles

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.operation.errors.FlotaleWorkflowErrors
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:13 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class WorkflowDuplicationRole(
    private val workflowStoreQuery: WorkflowStoreQuery,
) : OperationRole<String, Unit> {
    private val statuses = listOf(WorkflowStatus.ACTIVE)

    override fun String.operateRole() {
        if (workflowStoreQuery.existByKeyAndStatusIn(this, statuses)) {
            throw FlotaleWorkflowErrors.WORKFLOW_ALREADY_EXIST.unprocessableEntity()
        }
    }
}
