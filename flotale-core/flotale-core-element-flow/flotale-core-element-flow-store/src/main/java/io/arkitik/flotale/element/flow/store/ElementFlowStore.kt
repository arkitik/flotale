package io.arkitik.flotale.element.flow.store

import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.store.creator.ElementFlowDomainCreator
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.flotale.element.flow.store.updater.ElementFlowDomainUpdater
import io.arkitik.radix.develop.store.Store

interface ElementFlowStore : Store<String, ElementFlowDomain> {
    override val storeQuery: ElementFlowStoreQuery

    override fun identityCreator(): ElementFlowDomainCreator

    override fun ElementFlowDomain.identityUpdater(): ElementFlowDomainUpdater
}
