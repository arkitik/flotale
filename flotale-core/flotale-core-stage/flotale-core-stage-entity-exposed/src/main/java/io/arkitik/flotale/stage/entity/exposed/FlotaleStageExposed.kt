package io.arkitik.flotale.stage.entity.exposed

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowTable
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime

class FlotaleStageExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    val workflowUuid: String,
    override val stageKey: String,
    override val stageName: String,
    override var status: StageStatus,
    val database: Database?,
) : StageDomain {
    override val workflow: WorkflowDomain by lazy {
        FlotaleWorkflowTable.findIdentityByUuid(workflowUuid, database)!!
    }
}
