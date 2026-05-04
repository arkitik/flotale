package io.arkitik.flotale.task.entity.exposed

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime

class FlotaleTaskExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    val stageUuid: String,
    override val taskKey: String,
    override val taskName: String,
    override val terminalTask: Boolean,
    override var status: TaskStatus,
    val database: Database?,
) : TaskDomain {
    override val stage: StageDomain by lazy {
        FlotaleStageTable.findIdentityByUuid(stageUuid, database)!!
    }
}
