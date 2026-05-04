package io.arkitik.flotale.task.initial.entity.exposed

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime

class FlotaleTaskInitialExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    val stageUuid: String,
    val taskUuid: String,
    val database: Database?,
) : TaskInitialDomain {
    override val stage: StageDomain by lazy {
        FlotaleStageTable.findIdentityByUuid(stageUuid, database)!!
    }
    override val task: TaskDomain by lazy {
        FlotaleTaskTable.findIdentityByUuid(taskUuid, database)!!
    }
}
