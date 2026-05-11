package io.arkitik.flotale.workflow.sdk

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.sdk.dto.CreateWorkflowDto
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.operation.OperationRole

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 12:10 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleWorkflowDomainSdk {
    val createWorkflows: Operation<List<CreateWorkflowDto>, List<WorkflowDomain>>
    val createWorkflow: Operation<CreateWorkflowDto, WorkflowDomain>
    val findWorkflow: Operation<String, WorkflowDomain>
    val deleteWorkflow: Operation<WorkflowDomain, Unit>
    val workflowExistByKey: OperationRole<String, Boolean>
}
