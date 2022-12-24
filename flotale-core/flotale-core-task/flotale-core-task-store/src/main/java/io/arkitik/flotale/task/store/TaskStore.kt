package io.arkitik.flotale.task.store

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.store.creator.TaskDomainCreator
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.flotale.task.store.updater.TaskDomainUpdater
import io.arkitik.radix.develop.store.Store

interface TaskStore : Store<String, TaskDomain> {
    override val storeQuery: TaskStoreQuery

    override fun identityCreator(): TaskDomainCreator

    override fun TaskDomain.identityUpdater(): TaskDomainUpdater
}
