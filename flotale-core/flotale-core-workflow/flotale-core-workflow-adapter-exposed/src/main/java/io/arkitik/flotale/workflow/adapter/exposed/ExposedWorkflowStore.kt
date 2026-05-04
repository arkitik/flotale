package io.arkitik.flotale.workflow.adapter.exposed

import io.arkitik.flotale.workflow.adapter.exposed.creator.WorkflowDomainCreatorImpl
import io.arkitik.flotale.workflow.adapter.exposed.query.ExposedWorkflowStoreQuery
import io.arkitik.flotale.workflow.adapter.exposed.updater.WorkflowDomainUpdaterImpl
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowExposed
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowTable
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.flotale.workflow.store.creator.WorkflowDomainCreator
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.flotale.workflow.store.updater.WorkflowDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedWorkflowStore(
    database: Database?,
) : ExposedStore<String, WorkflowDomain, FlotaleWorkflowTable>(
    identityTable = FlotaleWorkflowTable,
    database = database,
), WorkflowStore {

    override val storeQuery: WorkflowStoreQuery = ExposedWorkflowStoreQuery(database)

    override fun identityCreator(): WorkflowDomainCreator = WorkflowDomainCreatorImpl()

    override fun WorkflowDomain.identityUpdater(): WorkflowDomainUpdater =
        WorkflowDomainUpdaterImpl(this as FlotaleWorkflowExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: WorkflowDomain) {
        identity as FlotaleWorkflowExposed
        this[identityTable.workflowKey] = identity.workflowKey
        this[identityTable.workflowName] = identity.workflowName
        this[identityTable.status] = identity.status
    }

    override fun <K : Any> UpdateBuilder<K>.updateEntity(identity: WorkflowDomain) {
        identity as FlotaleWorkflowExposed
        this[identityTable.status] = identity.status
    }
}
