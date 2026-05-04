package io.arkitik.flotale.workflow.operation.main

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.operation.errors.FlotaleWorkflowErrors
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:14 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class FindWorkflowOperation(
    private val workflowStoreQuery: WorkflowStoreQuery,
) : Operation<String, WorkflowDomain> {
    override fun String.operate() =
        workflowStoreQuery.findByKeyAndNotDeleted(this)
            .resourceNotFound(FlotaleWorkflowErrors.WORKFLOW_DOES_NOT_EXIST)
}
