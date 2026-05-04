package io.arkitik.flotale.action.entity.exposed

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime

class FlotaleActionExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    val sourceTaskUuid: String,
    val destinationTaskUuid: String,
    override val actionKey: String,
    override val actionName: String,
    override var status: ActionStatus,
    val database: Database?,
) : ActionDomain {
    override val sourceTask: TaskDomain by lazy {
        FlotaleTaskTable.findIdentityByUuid(sourceTaskUuid, database)!!
    }
    override val destinationTask: TaskDomain by lazy {
        FlotaleTaskTable.findIdentityByUuid(destinationTaskUuid, database)!!
    }
}
