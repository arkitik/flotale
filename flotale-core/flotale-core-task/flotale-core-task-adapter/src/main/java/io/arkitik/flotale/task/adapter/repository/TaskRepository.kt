package io.arkitik.flotale.task.adapter.repository

import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface TaskRepository : RadixRepository<String, FlotaleTask> {
    fun existsByTaskKeyAndStatusIn(
        actionKey: String,
        statuses: List<TaskStatus>,
    ): Boolean

    fun findFirstByTaskKeyAndStatusNotIn(
        actionKey: String,
        statuses: List<TaskStatus>,
    ): FlotaleTask?

    fun findAllByStageAndStatus(
        stage: FlotaleStage,
        status: TaskStatus,
    ): List<FlotaleTask>
}
