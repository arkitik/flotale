package io.arkitik.flotale.element.flow.adapter.exposed

import io.arkitik.flotale.element.flow.adapter.exposed.creator.ElementFlowDomainCreatorImpl
import io.arkitik.flotale.element.flow.adapter.exposed.query.ExposedElementFlowStoreQuery
import io.arkitik.flotale.element.flow.adapter.exposed.updater.ElementFlowDomainUpdaterImpl
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.exposed.FlotaleElementFlowExposed
import io.arkitik.flotale.element.flow.entity.exposed.FlotaleElementFlowTable
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.flow.store.creator.ElementFlowDomainCreator
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.flotale.element.flow.store.updater.ElementFlowDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedElementFlowStore(
    database: Database?,
) : ExposedStore<String, ElementFlowDomain, FlotaleElementFlowTable>(
    identityTable = FlotaleElementFlowTable,
    database = database,
), ElementFlowStore {

    override val storeQuery: ElementFlowStoreQuery = ExposedElementFlowStoreQuery(database)

    override fun identityCreator(): ElementFlowDomainCreator = ElementFlowDomainCreatorImpl(database)

    override fun ElementFlowDomain.identityUpdater(): ElementFlowDomainUpdater =
        ElementFlowDomainUpdaterImpl(this as FlotaleElementFlowExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: ElementFlowDomain) {
        identity as FlotaleElementFlowExposed
        this[identityTable.element] = identity.elementUuid
        this[identityTable.action] = identity.actionUuid
        this[identityTable.executedBy] = identity.executedBy
    }
}
