package io.arkitik.flotale.element.store

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.store.creator.ElementDomainCreator
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.flotale.element.store.updater.ElementDomainUpdater
import io.arkitik.radix.develop.store.Store

interface ElementStore : Store<String, ElementDomain> {
    override val storeQuery: ElementStoreQuery

    override fun identityCreator(): ElementDomainCreator

    override fun ElementDomain.identityUpdater(): ElementDomainUpdater
}
