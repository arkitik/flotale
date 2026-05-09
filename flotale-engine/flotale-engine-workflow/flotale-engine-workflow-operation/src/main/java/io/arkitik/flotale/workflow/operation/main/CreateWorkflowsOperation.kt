package io.arkitik.flotale.workflow.operation.main

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.sdk.dto.CreateWorkflowDto
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.creator

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 11:17 PM, 25/07/2025
 */
internal class CreateWorkflowsOperation(
    private val workflowStore: WorkflowStore,
) : Operation<List<CreateWorkflowDto>, List<WorkflowDomain>> {
    override fun List<CreateWorkflowDto>.operate() =
        with(workflowStore) {
            map { (workflowKey, workflowName) ->
                creator(identityCreator()) {
                    workflowKey.workflowKey()
                    workflowName.workflowName()
                    WorkflowStatus.ACTIVE.status()
                }
            }.insert().toList()
        }
}