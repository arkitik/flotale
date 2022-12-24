package io.arkitik.flotale.task.initial.adapter

import io.arkitik.flotale.task.initial.adapter.creator.TaskInitialDomainCreatorImpl
import io.arkitik.flotale.task.initial.adapter.query.TaskInitialStoreQueryImpl
import io.arkitik.flotale.task.initial.adapter.repository.TaskInitialRepository
import io.arkitik.flotale.task.initial.adapter.updater.TaskInitialDomainUpdaterImpl
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.flotale.task.initial.entity.FlotaleTaskInitial
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.initial.store.creator.TaskInitialDomainCreator
import io.arkitik.flotale.task.initial.store.query.TaskInitialStoreQuery
import io.arkitik.flotale.task.initial.store.updater.TaskInitialDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class TaskInitialStoreImpl(
    taskInitialRepository: TaskInitialRepository,
) : StoreImpl<String, TaskInitialDomain, FlotaleTaskInitial>(taskInitialRepository), TaskInitialStore {
    override val storeQuery: TaskInitialStoreQuery =
        TaskInitialStoreQueryImpl(taskInitialRepository)

    override fun TaskInitialDomain.map(): FlotaleTaskInitial = this as FlotaleTaskInitial

    override fun identityCreator(): TaskInitialDomainCreator = TaskInitialDomainCreatorImpl()

    override fun TaskInitialDomain.identityUpdater(): TaskInitialDomainUpdater =
        TaskInitialDomainUpdaterImpl(map())
}
