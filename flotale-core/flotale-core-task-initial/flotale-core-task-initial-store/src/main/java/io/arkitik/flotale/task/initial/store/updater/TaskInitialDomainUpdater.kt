package io.arkitik.flotale.task.initial.store.updater

import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface TaskInitialDomainUpdater : StoreIdentityUpdater<String, TaskInitialDomain>
