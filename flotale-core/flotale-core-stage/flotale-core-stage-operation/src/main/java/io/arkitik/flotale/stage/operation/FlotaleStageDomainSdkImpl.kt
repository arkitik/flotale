package io.arkitik.flotale.stage.operation

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.operation.main.CreateStageOperation
import io.arkitik.flotale.stage.operation.main.DeleteStageOperation
import io.arkitik.flotale.stage.operation.main.FindStageOperation
import io.arkitik.flotale.stage.operation.main.InitialWorkflowStageOperation
import io.arkitik.flotale.stage.operation.main.WorkflowStagesOperation
import io.arkitik.flotale.stage.operation.roles.StageDuplicationRole
import io.arkitik.flotale.stage.operation.roles.StageShouldBeNotDeleted
import io.arkitik.flotale.stage.operation.roles.WorkflowShouldNotHasAnotherInitialStage
import io.arkitik.flotale.stage.sdk.FlotaleStageDomainSdk
import io.arkitik.flotale.stage.sdk.dto.CreateStageDto
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.operation.ext.operateRole
import io.arkitik.radix.develop.operation.ext.operationBuilder

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleStageDomainSdkImpl(
    stageStore: StageStore,
    stageInitialStore: StageInitialStore,
) : FlotaleStageDomainSdk {
    private val workflowShouldNotHasAnotherInitialStage =
        WorkflowShouldNotHasAnotherInitialStage(stageInitialStore.storeQuery)
    override val workflowStages: Operation<WorkflowDomain, List<StageDomain>> =
        operationBuilder {
            mainOperation(WorkflowStagesOperation(stageStore.storeQuery))
        }
    private val stageDuplicationRole = StageDuplicationRole(stageStore.storeQuery)

    override val createStage: Operation<CreateStageDto, StageDomain> =
        operationBuilder {
            install {
                stageDuplicationRole
                    .operateRole(stageKey)
            }

            install {
                workflowShouldNotHasAnotherInitialStage.takeIf { initialStage }?.operateRole(workflow)
            }

            mainOperation(
                CreateStageOperation(
                    stageStore = stageStore,
                    stageInitialStore = stageInitialStore
                )
            )
        }

    override val findStage: Operation<String, StageDomain> =
        operationBuilder {
            mainOperation(FindStageOperation(stageStore.storeQuery))
        }

    override val deleteStage: Operation<StageDomain, Unit> =
        operationBuilder {
            install(StageShouldBeNotDeleted)
            mainOperation(
                DeleteStageOperation(
                    stageStore,
                    stageInitialStore
                )
            )
        }

    override val initialWorkflowStage: Operation<WorkflowDomain, StageDomain> =
        operationBuilder {
            mainOperation(InitialWorkflowStageOperation(stageInitialStore.storeQuery))
        }
}
