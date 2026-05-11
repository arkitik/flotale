package io.arkitik.flotale.workflow.operation

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.operation.main.CreateWorkflowOperation
import io.arkitik.flotale.workflow.operation.main.CreateWorkflowsOperation
import io.arkitik.flotale.workflow.operation.main.DeleteWorkflowOperation
import io.arkitik.flotale.workflow.operation.main.FindWorkflowOperation
import io.arkitik.flotale.workflow.operation.roles.WorkflowExistByKeyRole
import io.arkitik.flotale.workflow.operation.roles.WorkflowShouldBeNotDeleted
import io.arkitik.flotale.workflow.operation.roles.WorkflowsDuplicationRole
import io.arkitik.flotale.workflow.sdk.FlotaleWorkflowDomainSdk
import io.arkitik.flotale.workflow.sdk.dto.CreateWorkflowDto
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.operation.ext.operateRole
import io.arkitik.radix.develop.operation.ext.operationBuilder

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleWorkflowDomainSdkImpl(
    workflowStore: WorkflowStore,
) : FlotaleWorkflowDomainSdk {
    private val workflowDuplicationRole = WorkflowsDuplicationRole(
        workflowStoreQuery = workflowStore.storeQuery
    )

    override val createWorkflow: Operation<CreateWorkflowDto, WorkflowDomain> =
        operationBuilder {
            install {
                workflowDuplicationRole
                    .operateRole(listOf(workflowKey))
            }

            mainOperation(CreateWorkflowOperation(workflowStore = workflowStore))
        }

    override val createWorkflows: Operation<List<CreateWorkflowDto>, List<WorkflowDomain>> =
        operationBuilder {
            install {
                workflowDuplicationRole
                    .operateRole(map(CreateWorkflowDto::workflowKey))
            }
            mainOperation(
                CreateWorkflowsOperation(
                    workflowStore = workflowStore
                )
            )
        }

    override val findWorkflow: Operation<String, WorkflowDomain> =
        operationBuilder {
            mainOperation(FindWorkflowOperation(workflowStore.storeQuery))
        }

    override val deleteWorkflow: Operation<WorkflowDomain, Unit> =
        operationBuilder {
            install(WorkflowShouldBeNotDeleted)
            mainOperation(DeleteWorkflowOperation(workflowStore))
        }
    override val workflowExistByKey: OperationRole<String, Boolean> =
        WorkflowExistByKeyRole(
            workflowStoreQuery = workflowStore.storeQuery
        )
}
