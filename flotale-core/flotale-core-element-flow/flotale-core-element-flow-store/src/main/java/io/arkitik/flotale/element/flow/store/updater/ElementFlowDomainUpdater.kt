package io.arkitik.flotale.element.flow.store.updater

import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface ElementFlowDomainUpdater : StoreIdentityUpdater<String, ElementFlowDomain>
