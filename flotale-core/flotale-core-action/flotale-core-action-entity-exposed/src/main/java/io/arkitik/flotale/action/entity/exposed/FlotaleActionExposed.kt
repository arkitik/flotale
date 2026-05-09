package io.arkitik.flotale.action.entity.exposed

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.domain.embedded.ActionType
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
    override val actionType: ActionType,
    override var actionStatus: ActionStatus,
    override var actionMessage: String?,
    override var actionColor: String,
    override var actionHint: String?,
    override var actionOutlined: Boolean,
    override var successExecutionMessage: String?,
    override var failedExecutionMessage: String?,
    val database: Database?,
) : ActionDomain {
    override val sourceTask: TaskDomain by lazy {
        FlotaleTaskTable.findIdentityByUuid(sourceTaskUuid, database)!!
    }
    override val destinationTask: TaskDomain by lazy {
        FlotaleTaskTable.findIdentityByUuid(destinationTaskUuid, database)!!
    }
}
