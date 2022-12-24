package io.arkitik.flotale.workflow.adapter

import io.arkitik.flotale.workflow.adapter.creator.WorkflowDomainCreatorImpl
import io.arkitik.flotale.workflow.adapter.query.WorkflowStoreQueryImpl
import io.arkitik.flotale.workflow.adapter.repository.WorkflowRepository
import io.arkitik.flotale.workflow.adapter.updater.WorkflowDomainUpdaterImpl
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.flotale.workflow.store.creator.WorkflowDomainCreator
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.flotale.workflow.store.updater.WorkflowDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class WorkflowStoreImpl(
    workflowRepository: WorkflowRepository,
) : StoreImpl<String, WorkflowDomain, FlotaleWorkflow>(workflowRepository), WorkflowStore {
    override val storeQuery: WorkflowStoreQuery = WorkflowStoreQueryImpl(workflowRepository)

    override fun WorkflowDomain.map(): FlotaleWorkflow = this as FlotaleWorkflow

    override fun identityCreator(): WorkflowDomainCreator = WorkflowDomainCreatorImpl()

    override fun WorkflowDomain.identityUpdater(): WorkflowDomainUpdater =
        WorkflowDomainUpdaterImpl(map())
}
