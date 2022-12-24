package io.arkitik.flotale.task.store.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.radix.develop.store.query.StoreQuery

interface TaskStoreQuery : StoreQuery<String, TaskDomain> {
    fun existByKeyAndStatusIn(
        key: String,
        statuses: List<TaskStatus>,
    ): Boolean

    fun findByKeyAndNotDeleted(
        key: String,
    ): TaskDomain?

    fun allStageTasks(
        stage: StageDomain,
    ): List<TaskDomain>
}
