package io.arkitik.flotale.stage.initial.entity.exposed

import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowTable
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleStageInitialTable : RadixTable<String, StageInitialDomain>("flotale_stage_initial") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val workflow = reference(
        name = "workflow_uuid",
        refColumn = FlotaleWorkflowTable.uuid,
    )
    val stage = reference(
        name = "stage_uuid",
        refColumn = FlotaleStageTable.uuid,
    )

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): StageInitialDomain =
        FlotaleStageInitialExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            workflowUuid = resultRow[workflow],
            stageUuid = resultRow[stage],
            database = database,
        )
}
