package io.arkitik.flotale.task.initial.adapter.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.initial.adapter.repository.TaskInitialRepository
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.flotale.task.initial.entity.FlotaleTaskInitial
import io.arkitik.flotale.task.initial.store.query.TaskInitialStoreQuery
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class TaskInitialStoreQueryImpl(
    private val taskInitialRepository: TaskInitialRepository,
) : StoreQueryImpl<String, TaskInitialDomain, FlotaleTaskInitial>(taskInitialRepository),
    TaskInitialStoreQuery {
    override fun existByStage(stage: StageDomain) =
        taskInitialRepository.existsByStage(stage as FlotaleStage)

    override fun findByTask(task: TaskDomain) =
        taskInitialRepository.findByTask(task as FlotaleTask)

    override fun findByStage(stage: StageDomain): TaskInitialDomain? =
        taskInitialRepository.findByStage(stage as FlotaleStage)
}
