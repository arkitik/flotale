package io.arkitik.flotale.element.flow.entity.exposed

import io.arkitik.flotale.action.entity.exposed.FlotaleActionTable
import io.arkitik.flotale.element.entity.exposed.FlotaleElementTable
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleElementFlowTable : RadixTable<String, ElementFlowDomain>("flotale_element_flow") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val element = reference(
        name = "element_uuid",
        refColumn = FlotaleElementTable.uuid,
    )
    val action = reference(
        name = "action_uuid",
        refColumn = FlotaleActionTable.uuid,
    )
    val executedBy = varchar("executed_by", 255)

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): ElementFlowDomain =
        FlotaleElementFlowExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            elementUuid = resultRow[element],
            actionUuid = resultRow[action],
            executedBy = resultRow[executedBy],
            database = database,
        )
}
