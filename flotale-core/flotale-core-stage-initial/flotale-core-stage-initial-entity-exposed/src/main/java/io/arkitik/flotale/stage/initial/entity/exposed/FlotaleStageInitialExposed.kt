package io.arkitik.flotale.stage.initial.entity.exposed

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowTable
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime

class FlotaleStageInitialExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    val workflowUuid: String,
    val stageUuid: String,
    val database: Database?,
) : StageInitialDomain {
    override val workflow: WorkflowDomain by lazy {
        FlotaleWorkflowTable.findIdentityByUuid(workflowUuid, database)!!
    }
    override val stage: StageDomain by lazy {
        FlotaleStageTable.findIdentityByUuid(stageUuid, database)!!
    }
}
