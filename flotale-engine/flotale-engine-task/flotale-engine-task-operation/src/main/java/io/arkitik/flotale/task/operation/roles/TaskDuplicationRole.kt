package io.arkitik.flotale.task.operation.roles

import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.operation.errors.FlotaleTaskErrors
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:13 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class TaskDuplicationRole(
    private val taskStoreQuery: TaskStoreQuery,
) : OperationRole<String, Unit> {
    private val statuses = listOf(TaskStatus.ACTIVE)

    override fun String.operateRole() {
        if (taskStoreQuery.existByKeyAndStatusIn(this, statuses)) {
            throw FlotaleTaskErrors.TASK_ALREADY_EXIST.unprocessableEntity()
        }
    }
}
