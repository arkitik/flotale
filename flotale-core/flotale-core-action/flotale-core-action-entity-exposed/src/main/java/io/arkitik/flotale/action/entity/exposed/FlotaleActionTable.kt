package io.arkitik.flotale.action.entity.exposed

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.domain.embedded.ActionType
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleActionTable : RadixTable<String, ActionDomain>("flotale_action") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val sourceTask = reference(
        name = "source_task_uuid",
        refColumn = FlotaleTaskTable.uuid,
    )
    val destinationTask = reference(
        name = "destination_task_uuid",
        refColumn = FlotaleTaskTable.uuid,
    )
    val actionKey = varchar("action_key", 255)
    val actionName = varchar("action_name", 255)
    val actionType = enumerationByName<ActionType>("action_type", 50)
    val actionStatus = enumerationByName<ActionStatus>("action_status", 50)
    val actionMessage = varchar("action_message", 500).nullable()
    val actionColor = varchar("action_color", 50)
    val actionHint = varchar("action_hint", 500).nullable()
    val actionOutlined = bool("action_outlined")
    val successExecutionMessage = varchar("success_execution_message", 500).nullable()
    val failedExecutionMessage = varchar("failed_execution_message", 500).nullable()

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): ActionDomain =
        FlotaleActionExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            sourceTaskUuid = resultRow[sourceTask],
            destinationTaskUuid = resultRow[destinationTask],
            actionKey = resultRow[actionKey],
            actionName = resultRow[actionName],
            actionType = resultRow[actionType],
            actionStatus = resultRow[actionStatus],
            actionMessage = resultRow[actionMessage],
            actionColor = resultRow[actionColor],
            actionHint = resultRow[actionHint],
            actionOutlined = resultRow[actionOutlined],
            successExecutionMessage = resultRow[successExecutionMessage],
            failedExecutionMessage = resultRow[failedExecutionMessage],
            database = database,
        )
}
