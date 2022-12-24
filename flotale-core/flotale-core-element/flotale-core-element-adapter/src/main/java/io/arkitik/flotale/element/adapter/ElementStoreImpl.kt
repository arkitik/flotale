package io.arkitik.flotale.element.adapter

import io.arkitik.flotale.element.adapter.creator.ElementDomainCreatorImpl
import io.arkitik.flotale.element.adapter.query.ElementStoreQueryImpl
import io.arkitik.flotale.element.adapter.repository.ElementRepository
import io.arkitik.flotale.element.adapter.updater.ElementDomainUpdaterImpl
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.flotale.element.store.creator.ElementDomainCreator
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.flotale.element.store.updater.ElementDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class ElementStoreImpl(
    elementRepository: ElementRepository,
) : StoreImpl<String, ElementDomain, FlotaleElement>(elementRepository), ElementStore {
    override val storeQuery: ElementStoreQuery = ElementStoreQueryImpl(elementRepository)

    override fun ElementDomain.map(): FlotaleElement = this as FlotaleElement

    override fun identityCreator(): ElementDomainCreator = ElementDomainCreatorImpl()

    override fun ElementDomain.identityUpdater(): ElementDomainUpdater =
        ElementDomainUpdaterImpl(map())
}
