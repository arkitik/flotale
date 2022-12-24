package io.arkitik.flotale.stage.initial.adapter.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.stage.initial.adapter.repository.StageInitialRepository
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.stage.initial.entity.FlotaleStageInitial
import io.arkitik.flotale.stage.initial.store.query.StageInitialStoreQuery
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class StageInitialStoreQueryImpl(
    private val stageInitialRepository: StageInitialRepository,
) : StoreQueryImpl<String, StageInitialDomain, FlotaleStageInitial>(stageInitialRepository),
    StageInitialStoreQuery {
    override fun existByWorkflow(workflow: WorkflowDomain) =
        stageInitialRepository.existsByWorkflow(workflow as FlotaleWorkflow)

    override fun findByWorkflow(workflow: WorkflowDomain) =
        stageInitialRepository.findByWorkflow(workflow as FlotaleWorkflow)

    override fun findByStage(stage: StageDomain) =
        stageInitialRepository.findByStage(stage as FlotaleStage)
}
