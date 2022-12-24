package io.arkitik.flotale.task.adapter.updater

import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.store.updater.TaskDomainUpdater

internal class TaskDomainUpdaterImpl(
    private val entity: FlotaleTask,
) : TaskDomainUpdater {
    override fun TaskStatus.status(): TaskDomainUpdater {
        entity.status = this
        return this@TaskDomainUpdaterImpl
    }

    override fun update(): FlotaleTask = entity
}
