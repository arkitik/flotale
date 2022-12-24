package io.arkitik.flotale.stage.adapter.query

import io.arkitik.flotale.stage.adapter.repository.StageRepository
import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class StageStoreQueryImpl(
    private val stageRepository: StageRepository,
) : StoreQueryImpl<String, StageDomain, FlotaleStage>(stageRepository), StageStoreQuery {
    override fun existByKeyAndStatusIn(key: String, statuses: List<StageStatus>) =
        stageRepository.existsByStageKeyAndStatusIn(key, statuses)

    override fun findByKeyAndNotDeleted(key: String) =
        stageRepository.findFirstByStageKeyAndStatusNotIn(key, listOf(StageStatus.DELETED))

    override fun allWorkflowStages(workflow: WorkflowDomain) =
        stageRepository.findAllByWorkflowAndStatus(workflow as FlotaleWorkflow, StageStatus.ACTIVE)
}
