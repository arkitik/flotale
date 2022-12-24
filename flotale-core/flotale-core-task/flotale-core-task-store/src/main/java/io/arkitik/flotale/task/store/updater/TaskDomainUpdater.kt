package io.arkitik.flotale.task.store.updater

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface TaskDomainUpdater : StoreIdentityUpdater<String, TaskDomain> {
    fun TaskStatus.status(): TaskDomainUpdater
}
