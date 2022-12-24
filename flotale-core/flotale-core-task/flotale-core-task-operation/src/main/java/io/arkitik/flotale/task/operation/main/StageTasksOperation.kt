package io.arkitik.flotale.task.operation.main

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.radix.develop.operation.Operation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 8:55 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class StageTasksOperation(
    private val taskStoreQuery: TaskStoreQuery,
) : Operation<StageDomain, List<TaskDomain>> {
    override fun StageDomain.operate() =
        taskStoreQuery.allStageTasks(this)
}
