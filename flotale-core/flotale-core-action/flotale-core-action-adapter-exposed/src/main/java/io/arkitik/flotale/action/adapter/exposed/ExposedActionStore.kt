package io.arkitik.flotale.action.adapter.exposed

import io.arkitik.flotale.action.adapter.exposed.creator.ActionDomainCreatorImpl
import io.arkitik.flotale.action.adapter.exposed.query.ExposedActionStoreQuery
import io.arkitik.flotale.action.adapter.exposed.updater.ActionDomainUpdaterImpl
import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.entity.exposed.FlotaleActionExposed
import io.arkitik.flotale.action.entity.exposed.FlotaleActionTable
import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.flotale.action.store.creator.ActionDomainCreator
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.flotale.action.store.updater.ActionDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedActionStore(
    database: Database?,
) : ExposedStore<String, ActionDomain, FlotaleActionTable>(
    identityTable = FlotaleActionTable,
    database = database,
), ActionStore {

    override val storeQuery: ActionStoreQuery = ExposedActionStoreQuery(database)

    override fun identityCreator(): ActionDomainCreator = ActionDomainCreatorImpl(database)

    override fun ActionDomain.identityUpdater(): ActionDomainUpdater =
        ActionDomainUpdaterImpl(this as FlotaleActionExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: ActionDomain) {
        identity as FlotaleActionExposed
        this[identityTable.sourceTask] = identity.sourceTaskUuid
        this[identityTable.destinationTask] = identity.destinationTaskUuid
        this[identityTable.actionKey] = identity.actionKey
        this[identityTable.actionName] = identity.actionName
        this[identityTable.status] = identity.status
    }

    override fun <K : Any> UpdateBuilder<K>.updateEntity(identity: ActionDomain) {
        identity as FlotaleActionExposed
        this[identityTable.status] = identity.status
    }
}
