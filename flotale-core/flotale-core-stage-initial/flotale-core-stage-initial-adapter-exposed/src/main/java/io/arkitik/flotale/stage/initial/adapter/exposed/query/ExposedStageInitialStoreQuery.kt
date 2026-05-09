package io.arkitik.flotale.stage.initial.adapter.exposed.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.stage.initial.entity.exposed.FlotaleStageInitialTable
import io.arkitik.flotale.stage.initial.store.query.StageInitialStoreQuery
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.selectAll

internal class ExposedStageInitialStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, StageInitialDomain, FlotaleStageInitialTable>(FlotaleStageInitialTable, database),
    StageInitialStoreQuery {

    override fun existByWorkflow(workflow: WorkflowDomain): Boolean =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.workflow.eq(workflow.uuid) }
                .exist()
        }

    override fun findByWorkflow(workflow: WorkflowDomain): StageInitialDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.workflow.eq(workflow.uuid) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }

    override fun findByStage(stage: StageDomain): StageInitialDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.stage.eq(stage.uuid) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }
}
