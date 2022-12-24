package io.arkitik.flotale.stage.initial.store.updater

import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface StageInitialDomainUpdater : StoreIdentityUpdater<String, StageInitialDomain>
