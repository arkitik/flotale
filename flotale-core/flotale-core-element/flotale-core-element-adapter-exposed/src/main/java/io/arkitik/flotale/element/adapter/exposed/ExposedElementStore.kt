package io.arkitik.flotale.element.adapter.exposed

import io.arkitik.flotale.element.adapter.exposed.creator.ElementDomainCreatorImpl
import io.arkitik.flotale.element.adapter.exposed.query.ExposedElementStoreQuery
import io.arkitik.flotale.element.adapter.exposed.updater.ElementDomainUpdaterImpl
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.exposed.FlotaleElementExposed
import io.arkitik.flotale.element.entity.exposed.FlotaleElementTable
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.flotale.element.store.creator.ElementDomainCreator
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.flotale.element.store.updater.ElementDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedElementStore(
    database: Database?,
) : ExposedStore<String, ElementDomain, FlotaleElementTable>(
    identityTable = FlotaleElementTable,
    database = database,
), ElementStore {

    override val storeQuery: ElementStoreQuery = ExposedElementStoreQuery(database)

    override fun identityCreator(): ElementDomainCreator = ElementDomainCreatorImpl(database)

    override fun ElementDomain.identityUpdater(): ElementDomainUpdater =
        ElementDomainUpdaterImpl(this as FlotaleElementExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: ElementDomain) {
        identity as FlotaleElementExposed
        this[identityTable.elementKey] = identity.elementKey
        this[identityTable.task] = identity.taskUuid
        this[identityTable.addedBy] = identity.addedBy
    }

    override fun <K : Any> UpdateBuilder<K>.updateEntity(identity: ElementDomain) {
        identity as FlotaleElementExposed
        this[identityTable.task] = identity.taskUuid
    }
}
