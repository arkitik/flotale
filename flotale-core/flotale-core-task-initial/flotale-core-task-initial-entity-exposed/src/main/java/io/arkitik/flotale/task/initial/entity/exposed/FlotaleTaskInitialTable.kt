package io.arkitik.flotale.task.initial.entity.exposed

import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleTaskInitialTable : RadixTable<String, TaskInitialDomain>("flotale_task_initial") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val stage = reference(
        name = "stage_uuid",
        refColumn = FlotaleStageTable.uuid,
    )
    val task = reference(
        name = "task_uuid",
        refColumn = FlotaleTaskTable.uuid,
    )

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): TaskInitialDomain =
        FlotaleTaskInitialExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            stageUuid = resultRow[stage],
            taskUuid = resultRow[task],
            database = database,
        )
}
