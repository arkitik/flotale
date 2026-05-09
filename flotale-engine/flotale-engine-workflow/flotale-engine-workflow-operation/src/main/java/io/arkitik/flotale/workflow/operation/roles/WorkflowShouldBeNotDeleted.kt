package io.arkitik.flotale.workflow.operation.roles

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.operation.errors.FlotaleWorkflowErrors
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:40 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal object WorkflowShouldBeNotDeleted : OperationRole<WorkflowDomain, Unit> {
    override fun WorkflowDomain.operateRole() {
        if (status != WorkflowStatus.ACTIVE) {
            throw FlotaleWorkflowErrors.WORKFLOW_DOES_NOT_EXIST.unprocessableEntity()
        }
    }
}
