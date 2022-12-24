package io.arkitik.flotale.task.initial.adapter.updater

import io.arkitik.flotale.task.initial.entity.FlotaleTaskInitial
import io.arkitik.flotale.task.initial.store.updater.TaskInitialDomainUpdater

internal class TaskInitialDomainUpdaterImpl(
    private val entity: FlotaleTaskInitial,
) : TaskInitialDomainUpdater {
    override fun update(): FlotaleTaskInitial = entity
}
