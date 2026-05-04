package io.arkitik.flotale.task.initial.adapter.exposed.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.flotale.task.initial.entity.exposed.FlotaleTaskInitialTable
import io.arkitik.flotale.task.initial.store.query.TaskInitialStoreQuery
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.selectAll

internal class ExposedTaskInitialStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, TaskInitialDomain, FlotaleTaskInitialTable>(FlotaleTaskInitialTable, database),
    TaskInitialStoreQuery {

    override fun existByStage(stage: StageDomain): Boolean =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.stage.eq(stage.uuid) }
                .exist()
        }

    override fun findByTask(task: TaskDomain): TaskInitialDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.task.eq(task.uuid) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }

    override fun findByStage(stage: StageDomain): TaskInitialDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.stage.eq(stage.uuid) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }
}
