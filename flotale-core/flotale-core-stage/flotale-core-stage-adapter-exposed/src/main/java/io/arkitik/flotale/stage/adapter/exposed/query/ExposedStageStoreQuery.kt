package io.arkitik.flotale.stage.adapter.exposed.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.selectAll

internal class ExposedStageStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, StageDomain, FlotaleStageTable>(FlotaleStageTable, database),
    StageStoreQuery {

    override fun existByKeyAndStatusIn(key: String, statuses: List<StageStatus>): Boolean =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.stageKey.eq(key).and(identityTable.status.inList(statuses)) }
                .exist()
        }

    override fun findByKeyAndActive(key: String): StageDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.stageKey.eq(key).and(identityTable.status.eq(StageStatus.ACTIVE)) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }

    override fun allWorkflowStagesAndActive(workflow: WorkflowDomain): List<StageDomain> =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.workflow.eq(workflow.uuid).and(identityTable.status.eq(StageStatus.ACTIVE)) }
                .map { identityTable.mapToIdentity(it, database) }
        }
}
