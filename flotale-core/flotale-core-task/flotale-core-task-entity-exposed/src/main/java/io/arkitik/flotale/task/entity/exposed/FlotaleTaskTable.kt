package io.arkitik.flotale.task.entity.exposed

import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleTaskTable : RadixTable<String, TaskDomain>("flotale_task") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val stage = reference(
        name = "stage_uuid",
        refColumn = FlotaleStageTable.uuid,
    )
    val taskKey = varchar("task_key", 255)
    val taskName = varchar("task_name", 255)
    val terminalTask = bool("terminal_task")
    val status = enumerationByName<TaskStatus>("status", 50)

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): TaskDomain =
        FlotaleTaskExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            stageUuid = resultRow[stage],
            taskKey = resultRow[taskKey],
            taskName = resultRow[taskName],
            terminalTask = resultRow[terminalTask],
            status = resultRow[status],
            database = database,
        )
}
