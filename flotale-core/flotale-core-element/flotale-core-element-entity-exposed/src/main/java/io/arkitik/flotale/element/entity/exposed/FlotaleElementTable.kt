package io.arkitik.flotale.element.entity.exposed

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import io.arkitik.radix.develop.exposed.table.RadixTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database

object FlotaleElementTable : RadixTable<String, ElementDomain>("flotale_element") {
    override val uuid: Column<String> = varchar("uuid", 255)
    val elementKey = varchar("element_key", 255)
    val elementType = varchar("element_type", 255)
    val task = reference(
        name = "task_uuid",
        refColumn = FlotaleTaskTable.uuid,
    )
    val addedBy = varchar("added_by", 255)

    init {
        uniqueIndex(customIndexName = "flotale_element_unique", elementKey, elementType)
    }

    override fun mapToIdentity(resultRow: ResultRow, database: Database?): ElementDomain =
        FlotaleElementExposed(
            uuid = resultRow[uuid],
            creationDate = resultRow[creationDate],
            elementKey = resultRow[elementKey],
            elementType = resultRow[elementType],
            taskUuid = resultRow[task],
            addedBy = resultRow[addedBy],
            database = database,
        )
}
