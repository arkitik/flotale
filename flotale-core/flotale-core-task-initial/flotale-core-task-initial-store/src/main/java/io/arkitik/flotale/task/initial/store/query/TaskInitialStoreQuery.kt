package io.arkitik.flotale.task.initial.store.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.radix.develop.store.query.StoreQuery

interface TaskInitialStoreQuery : StoreQuery<String, TaskInitialDomain> {
    fun existByStage(stage: StageDomain): Boolean
    fun findByTask(task: TaskDomain): TaskInitialDomain?
    fun findByStage(stage: StageDomain): TaskInitialDomain?

}
