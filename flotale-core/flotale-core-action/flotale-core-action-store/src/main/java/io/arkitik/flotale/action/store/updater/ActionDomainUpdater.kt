package io.arkitik.flotale.action.store.updater

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface ActionDomainUpdater : StoreIdentityUpdater<String, ActionDomain> {
    fun ActionStatus.status(): ActionDomainUpdater
}
