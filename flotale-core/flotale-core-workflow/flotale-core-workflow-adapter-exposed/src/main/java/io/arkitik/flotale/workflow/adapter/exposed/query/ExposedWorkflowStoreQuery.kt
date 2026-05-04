package io.arkitik.flotale.workflow.adapter.exposed.query

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowTable
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.neq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.selectAll

internal class ExposedWorkflowStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, WorkflowDomain, FlotaleWorkflowTable>(FlotaleWorkflowTable, database),
    WorkflowStoreQuery {

    override fun existByKeyInAndStatusIn(keys: List<String>, statuses: List<WorkflowStatus>): Boolean =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.workflowKey.inList(keys).and(identityTable.status.inList(statuses)) }
                .exist()
        }

    override fun findByKeyAndNotDeleted(key: String): WorkflowDomain? =
        ensureInTransaction(database) {
            identityTable.selectAll()
                .where { identityTable.workflowKey.eq(key).and(identityTable.status.neq(WorkflowStatus.DELETED)) }
                .singleOrNull()
                ?.let { identityTable.mapToIdentity(it, database) }
        }
}
