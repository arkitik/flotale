package io.arkitik.flotale.stage.store.updater

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface StageDomainUpdater : StoreIdentityUpdater<String, StageDomain> {
    fun StageStatus.status(): StageDomainUpdater
}
