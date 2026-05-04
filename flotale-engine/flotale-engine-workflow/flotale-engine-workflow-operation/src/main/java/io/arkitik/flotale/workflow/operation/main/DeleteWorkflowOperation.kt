package io.arkitik.flotale.workflow.operation.main

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.storeUpdaterWithUpdate

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:29 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class DeleteWorkflowOperation(
    private val workflowStore: WorkflowStore,
) : Operation<WorkflowDomain, Unit> {
    override fun WorkflowDomain.operate() {
        with(workflowStore) {
            storeUpdaterWithUpdate(identityUpdater()) {
                WorkflowStatus.DELETED.status()
                update()
            }
        }

    }
}
