package io.arkitik.flotale.task.adapter

import io.arkitik.flotale.task.adapter.creator.TaskDomainCreatorImpl
import io.arkitik.flotale.task.adapter.query.TaskStoreQueryImpl
import io.arkitik.flotale.task.adapter.repository.TaskRepository
import io.arkitik.flotale.task.adapter.updater.TaskDomainUpdaterImpl
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.flotale.task.store.creator.TaskDomainCreator
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.flotale.task.store.updater.TaskDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class TaskStoreImpl(
    taskRepository: TaskRepository,
) : StoreImpl<String, TaskDomain, FlotaleTask>(taskRepository), TaskStore {
    override val storeQuery: TaskStoreQuery = TaskStoreQueryImpl(taskRepository)

    override fun TaskDomain.map(): FlotaleTask = this as FlotaleTask

    override fun identityCreator(): TaskDomainCreator = TaskDomainCreatorImpl()

    override fun TaskDomain.identityUpdater(): TaskDomainUpdater = TaskDomainUpdaterImpl(map())
}
