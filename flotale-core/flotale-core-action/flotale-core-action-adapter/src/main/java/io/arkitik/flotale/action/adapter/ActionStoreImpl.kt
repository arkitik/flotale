package io.arkitik.flotale.action.adapter

import io.arkitik.flotale.action.adapter.creator.ActionDomainCreatorImpl
import io.arkitik.flotale.action.adapter.query.ActionStoreQueryImpl
import io.arkitik.flotale.action.adapter.repository.ActionRepository
import io.arkitik.flotale.action.adapter.updater.ActionDomainUpdaterImpl
import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.flotale.action.store.creator.ActionDomainCreator
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.flotale.action.store.updater.ActionDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class ActionStoreImpl(
    actionRepository: ActionRepository,
) : StoreImpl<String, ActionDomain, FlotaleAction>(actionRepository), ActionStore {
    override val storeQuery: ActionStoreQuery = ActionStoreQueryImpl(actionRepository)

    override fun ActionDomain.map(): FlotaleAction = this as FlotaleAction

    override fun identityCreator(): ActionDomainCreator = ActionDomainCreatorImpl()

    override fun ActionDomain.identityUpdater(): ActionDomainUpdater =
        ActionDomainUpdaterImpl(map())
}
