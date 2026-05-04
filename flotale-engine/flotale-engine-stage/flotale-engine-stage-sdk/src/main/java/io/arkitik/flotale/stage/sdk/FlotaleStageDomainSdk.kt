package io.arkitik.flotale.stage.sdk

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.sdk.dto.CreateStageDto
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.operation.Operation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 12:10 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleStageDomainSdk {
    val createStage: Operation<CreateStageDto, StageDomain>
    val findStage: Operation<String, StageDomain>
    val deleteStage: Operation<StageDomain, Unit>

    val workflowStages: Operation<WorkflowDomain, List<StageDomain>>

    val initialWorkflowStage: Operation<WorkflowDomain, StageDomain>
}
