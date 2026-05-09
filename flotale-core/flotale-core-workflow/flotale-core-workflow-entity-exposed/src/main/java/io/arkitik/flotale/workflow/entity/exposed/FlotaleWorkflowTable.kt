package io.arkitik.flotale.workflow.entity.exposed

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleWorkflowTable : RadixTable<String, WorkflowDomain>("flotale_workflow") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val workflowKey = varchar("workflow_key", 255)
    val workflowName = varchar("workflow_name", 255)
    val status = enumerationByName<WorkflowStatus>("status", 50)

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): WorkflowDomain =
        FlotaleWorkflowExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            workflowKey = resultRow[workflowKey],
            workflowName = resultRow[workflowName],
            status = resultRow[status],
        )
}
