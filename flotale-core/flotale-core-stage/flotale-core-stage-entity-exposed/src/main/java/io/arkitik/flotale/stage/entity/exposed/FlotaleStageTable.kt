package io.arkitik.flotale.stage.entity.exposed

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowTable
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleStageTable : RadixTable<String, StageDomain>("flotale_stage") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val workflow = reference(
        name = "workflow_uuid",
        refColumn = FlotaleWorkflowTable.uuid,
    )
    val stageKey = varchar("stage_key", 255)
    val stageName = varchar("stage_name", 255)
    val status = enumerationByName<StageStatus>("status", 50)

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): StageDomain =
        FlotaleStageExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            workflowUuid = resultRow[workflow],
            stageKey = resultRow[stageKey],
            stageName = resultRow[stageName],
            status = resultRow[status],
            database = database,
        )
}
