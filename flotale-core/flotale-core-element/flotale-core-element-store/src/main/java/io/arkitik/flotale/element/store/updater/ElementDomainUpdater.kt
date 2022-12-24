package io.arkitik.flotale.element.store.updater

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface ElementDomainUpdater : StoreIdentityUpdater<String, ElementDomain> {
    fun TaskDomain.task(): ElementDomainUpdater
}
