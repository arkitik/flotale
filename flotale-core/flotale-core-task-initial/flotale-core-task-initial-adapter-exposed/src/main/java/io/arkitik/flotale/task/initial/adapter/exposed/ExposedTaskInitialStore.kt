package io.arkitik.flotale.task.initial.adapter.exposed

import io.arkitik.flotale.task.initial.adapter.exposed.creator.TaskInitialDomainCreatorImpl
import io.arkitik.flotale.task.initial.adapter.exposed.query.ExposedTaskInitialStoreQuery
import io.arkitik.flotale.task.initial.adapter.exposed.updater.TaskInitialDomainUpdaterImpl
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.flotale.task.initial.entity.exposed.FlotaleTaskInitialExposed
import io.arkitik.flotale.task.initial.entity.exposed.FlotaleTaskInitialTable
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.initial.store.creator.TaskInitialDomainCreator
import io.arkitik.flotale.task.initial.store.query.TaskInitialStoreQuery
import io.arkitik.flotale.task.initial.store.updater.TaskInitialDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedTaskInitialStore(
    database: Database?,
) : ExposedStore<String, TaskInitialDomain, FlotaleTaskInitialTable>(
    identityTable = FlotaleTaskInitialTable,
    database = database,
), TaskInitialStore {

    override val storeQuery: TaskInitialStoreQuery = ExposedTaskInitialStoreQuery(database)

    override fun identityCreator(): TaskInitialDomainCreator = TaskInitialDomainCreatorImpl(database)

    override fun TaskInitialDomain.identityUpdater(): TaskInitialDomainUpdater =
        TaskInitialDomainUpdaterImpl(this as FlotaleTaskInitialExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: TaskInitialDomain) {
        identity as FlotaleTaskInitialExposed
        this[identityTable.stage] = identity.stageUuid
        this[identityTable.task] = identity.taskUuid
    }

    override fun <K : Any> UpdateBuilder<K>.updateEntity(identity: TaskInitialDomain) {
        // No mutable fields
    }
}
