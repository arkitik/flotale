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
    val status = enumerationByName<ActionStatus>("status", 50)

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): ActionDomain =
        FlotaleActionExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            sourceTaskUuid = resultRow[sourceTask],
            destinationTaskUuid = resultRow[destinationTask],
            actionKey = resultRow[actionKey],
            actionName = resultRow[actionName],
            actionType = resultRow[actionType],
            status = resultRow[status],
            database = database,
        )
}
