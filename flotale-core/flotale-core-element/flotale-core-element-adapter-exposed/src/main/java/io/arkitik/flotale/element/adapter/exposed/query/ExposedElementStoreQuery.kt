package io.arkitik.flotale.element.adapter.exposed.query

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.exposed.FlotaleElementTable
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.selectAll

internal class ExposedElementStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, ElementDomain, FlotaleElementTable>(FlotaleElementTable, database),
    ElementStoreQuery {

    override fun existByElementKey(elementKey: String): Boolean =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.elementKey.eq(elementKey) }
                .exist()
        }

    override fun findByElementKey(elementKey: String): ElementDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.elementKey.eq(elementKey) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }
}
