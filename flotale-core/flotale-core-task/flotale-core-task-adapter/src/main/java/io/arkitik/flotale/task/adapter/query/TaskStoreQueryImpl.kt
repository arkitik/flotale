package io.arkitik.flotale.task.adapter.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.adapter.repository.TaskRepository
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class TaskStoreQueryImpl(
    private val taskRepository: TaskRepository,
) : StoreQueryImpl<String, TaskDomain, FlotaleTask>(taskRepository), TaskStoreQuery {
    override fun existByKeyAndStatusIn(key: String, statuses: List<TaskStatus>) =
        taskRepository.existsByTaskKeyAndStatusIn(key, statuses)

    override fun findByKeyAndNotDeleted(key: String) =
        taskRepository.findFirstByTaskKeyAndStatusNotIn(key, listOf(TaskStatus.DELETED))

    override fun allStageTasks(stage: StageDomain) =
        taskRepository.findAllByStageAndStatus(stage as FlotaleStage, TaskStatus.ACTIVE)
}
