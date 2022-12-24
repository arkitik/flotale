package io.arkitik.flotale.element.flow.adapter

import io.arkitik.flotale.element.flow.adapter.creator.ElementFlowDomainCreatorImpl
import io.arkitik.flotale.element.flow.adapter.query.ElementFlowStoreQueryImpl
import io.arkitik.flotale.element.flow.adapter.repository.ElementFlowRepository
import io.arkitik.flotale.element.flow.adapter.updater.ElementFlowDomainUpdaterImpl
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.FlotaleElementFlow
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.flow.store.creator.ElementFlowDomainCreator
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.flotale.element.flow.store.updater.ElementFlowDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class ElementFlowStoreImpl(
    elementFlowRepository: ElementFlowRepository,
) : StoreImpl<String, ElementFlowDomain, FlotaleElementFlow>(elementFlowRepository), ElementFlowStore {
    override val storeQuery: ElementFlowStoreQuery =
        ElementFlowStoreQueryImpl(elementFlowRepository)

    override fun ElementFlowDomain.map(): FlotaleElementFlow = this as FlotaleElementFlow

    override fun identityCreator(): ElementFlowDomainCreator = ElementFlowDomainCreatorImpl()

    override fun ElementFlowDomain.identityUpdater(): ElementFlowDomainUpdater =
        ElementFlowDomainUpdaterImpl(map())
}
