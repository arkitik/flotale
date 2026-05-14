package io.arkitik.flotale.element.flow.adapter.exposed.query

import io.arkitik.flotale.element.entity.exposed.FlotaleElementTable
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.exposed.FlotaleElementFlowTable
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.select

internal class ExposedElementFlowStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, ElementFlowDomain, FlotaleElementFlowTable>(FlotaleElementFlowTable, database),
    ElementFlowStoreQuery {

    override fun findAllByElement(
        elementKey: String,
        elementType: String,
        ascending: Boolean,
    ): List<ElementFlowDomain> =
        ensureInTransaction(database) {
            val sortDirection = if (ascending) SortOrder.ASC else SortOrder.DESC
            identityTable
                .innerJoin(FlotaleElementTable)
                .select(identityTable.columns)
                .where {
                    FlotaleElementTable.elementKey.eq(elementKey)
                        .and(FlotaleElementTable.elementType.eq(elementType))
                }
                .orderBy(identityTable.creationDate, sortDirection)
                .map { identityTable.mapToIdentity(it, database) }
        }
}
