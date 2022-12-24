package io.arkitik.flotale.action.store

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.store.creator.ActionDomainCreator
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.flotale.action.store.updater.ActionDomainUpdater
import io.arkitik.radix.develop.store.Store

interface ActionStore : Store<String, ActionDomain> {
    override val storeQuery: ActionStoreQuery

    override fun identityCreator(): ActionDomainCreator

    override fun ActionDomain.identityUpdater(): ActionDomainUpdater
}
