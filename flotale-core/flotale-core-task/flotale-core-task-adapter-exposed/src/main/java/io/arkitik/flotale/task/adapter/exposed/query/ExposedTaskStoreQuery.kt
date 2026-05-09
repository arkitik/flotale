package io.arkitik.flotale.task.adapter.exposed.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.neq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.selectAll

internal class ExposedTaskStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, TaskDomain, FlotaleTaskTable>(FlotaleTaskTable, database),
    TaskStoreQuery {

    override fun existByKeyAndStatusIn(key: String, statuses: List<TaskStatus>): Boolean =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.taskKey.eq(key).and(identityTable.status.inList(statuses)) }
                .exist()
        }

    override fun findByKeyAndNotDeleted(key: String): TaskDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.taskKey.eq(key).and(identityTable.status.neq(TaskStatus.DELETED)) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }

    override fun allStageTasks(stage: StageDomain): List<TaskDomain> =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.stage.eq(stage.uuid).and(identityTable.status.eq(TaskStatus.ACTIVE)) }
                .map { identityTable.mapToIdentity(it, database) }
        }
}
