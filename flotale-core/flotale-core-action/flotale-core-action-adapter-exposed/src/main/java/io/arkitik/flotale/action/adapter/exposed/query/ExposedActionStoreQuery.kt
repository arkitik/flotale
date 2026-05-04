package io.arkitik.flotale.action.adapter.exposed.query

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.entity.exposed.FlotaleActionTable
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.neq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.selectAll

internal class ExposedActionStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, ActionDomain, FlotaleActionTable>(FlotaleActionTable, database),
    ActionStoreQuery {

    override fun existByKeyAndStatusIn(key: String, statuses: List<ActionStatus>): Boolean =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.actionKey.eq(key).and(identityTable.status.inList(statuses)) }
                .exist()
        }

    override fun findByKeyAndNotDeleted(key: String): ActionDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.actionKey.eq(key).and(identityTable.status.neq(ActionStatus.DELETED)) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }

    override fun allTaskActionsAndActive(task: TaskDomain): List<ActionDomain> =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.sourceTask.eq(task.uuid).and(identityTable.status.eq(ActionStatus.ACTIVE)) }
                .map { identityTable.mapToIdentity(it, database) }
        }

    override fun findBySourceTaskAndActionKey(task: TaskDomain, key: String): ActionDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.sourceTask.eq(task.uuid).and(identityTable.actionKey.eq(key)) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }
}
