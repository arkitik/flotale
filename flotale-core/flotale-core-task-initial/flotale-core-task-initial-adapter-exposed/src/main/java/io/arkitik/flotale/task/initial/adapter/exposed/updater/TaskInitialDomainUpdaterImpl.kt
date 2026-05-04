package io.arkitik.flotale.task.initial.adapter.exposed.updater

import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.flotale.task.initial.entity.exposed.FlotaleTaskInitialExposed
import io.arkitik.flotale.task.initial.store.updater.TaskInitialDomainUpdater

internal class TaskInitialDomainUpdaterImpl(
    private val entity: FlotaleTaskInitialExposed,
) : TaskInitialDomainUpdater {

    override fun update(): TaskInitialDomain = entity
}
