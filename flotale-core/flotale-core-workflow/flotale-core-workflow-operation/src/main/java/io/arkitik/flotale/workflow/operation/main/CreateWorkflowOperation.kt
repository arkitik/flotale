package io.arkitik.flotale.workflow.operation.main

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.sdk.dto.CreateWorkflowDto
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.creatorWithSave

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class CreateWorkflowOperation(
    private val workflowStore: WorkflowStore,
) : Operation<CreateWorkflowDto, Unit> {
    override fun CreateWorkflowDto.operate() {
        with(workflowStore) {
            creatorWithSave(identityCreator()) {
                workflowKey.workflowKey()
                workflowName.workflowName()
                WorkflowStatus.ACTIVE.status()
            }
        }
    }
}
