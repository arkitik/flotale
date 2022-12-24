package io.arkitik.flotale.workflow.store

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.store.creator.WorkflowDomainCreator
import io.arkitik.flotale.workflow.store.query.WorkflowStoreQuery
import io.arkitik.flotale.workflow.store.updater.WorkflowDomainUpdater
import io.arkitik.radix.develop.store.Store

interface WorkflowStore : Store<String, WorkflowDomain> {
    override val storeQuery: WorkflowStoreQuery

    override fun identityCreator(): WorkflowDomainCreator

    override fun WorkflowDomain.identityUpdater(): WorkflowDomainUpdater
}
