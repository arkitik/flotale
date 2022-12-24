package io.arkitik.flotale.stage.adapter.repository

import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface StageRepository : RadixRepository<String, FlotaleStage> {
    fun existsByStageKeyAndStatusIn(
        actionKey: String,
        statuses: List<StageStatus>,
    ): Boolean

    fun findFirstByStageKeyAndStatusNotIn(
        actionKey: String,
        statuses: List<StageStatus>,
    ): FlotaleStage?

    fun findAllByWorkflowAndStatus(
        workflow: FlotaleWorkflow,
        status: StageStatus,
    ): List<FlotaleStage>
}
