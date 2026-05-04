package io.arkitik.flotale.task.adapter.exposed.updater

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskExposed
import io.arkitik.flotale.task.store.updater.TaskDomainUpdater

internal class TaskDomainUpdaterImpl(
    private val entity: FlotaleTaskExposed,
) : TaskDomainUpdater {

    override fun TaskStatus.status(): TaskDomainUpdater {
        entity.status = this
        return this@TaskDomainUpdaterImpl
    }

    override fun update(): TaskDomain = entity
}
