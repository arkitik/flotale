package io.arkitik.flotale.task.adapter.exposed

import io.arkitik.flotale.task.adapter.exposed.creator.TaskDomainCreatorImpl
import io.arkitik.flotale.task.adapter.exposed.query.ExposedTaskStoreQuery
import io.arkitik.flotale.task.adapter.exposed.updater.TaskDomainUpdaterImpl
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskExposed
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.flotale.task.store.creator.TaskDomainCreator
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.flotale.task.store.updater.TaskDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedTaskStore(
    database: Database?,
) : ExposedStore<String, TaskDomain, FlotaleTaskTable>(
    identityTable = FlotaleTaskTable,
    database = database,
), TaskStore {

    override val storeQuery: TaskStoreQuery = ExposedTaskStoreQuery(database)

    override fun identityCreator(): TaskDomainCreator = TaskDomainCreatorImpl(database)

    override fun TaskDomain.identityUpdater(): TaskDomainUpdater =
        TaskDomainUpdaterImpl(this as FlotaleTaskExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: TaskDomain) {
        identity as FlotaleTaskExposed
        this[identityTable.stage] = identity.stageUuid
        this[identityTable.taskKey] = identity.taskKey
        this[identityTable.taskName] = identity.taskName
        this[identityTable.terminalTask] = identity.terminalTask
        this[identityTable.status] = identity.status
    }

    override fun <K : Any> UpdateBuilder<K>.updateEntity(identity: TaskDomain) {
        identity as FlotaleTaskExposed
        this[identityTable.status] = identity.status
    }
}
