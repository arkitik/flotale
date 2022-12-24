package io.arkitik.flotale.task.initial.store

import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.flotale.task.initial.store.creator.TaskInitialDomainCreator
import io.arkitik.flotale.task.initial.store.query.TaskInitialStoreQuery
import io.arkitik.flotale.task.initial.store.updater.TaskInitialDomainUpdater
import io.arkitik.radix.develop.store.Store

interface TaskInitialStore : Store<String, TaskInitialDomain> {
    override val storeQuery: TaskInitialStoreQuery

    override fun identityCreator(): TaskInitialDomainCreator

    override fun TaskInitialDomain.identityUpdater(): TaskInitialDomainUpdater
}
