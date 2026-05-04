package io.arkitik.flotale.task.operation.roles

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.operation.errors.FlotaleTaskErrors
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:40 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal object TaskShouldBeNotDeleted : OperationRole<TaskDomain, Unit> {
    private val taskStatuses = listOf(TaskStatus.ACTIVE)

    override fun TaskDomain.operateRole() {
        if (!taskStatuses.contains(status)) {
            throw FlotaleTaskErrors.TASK_DOES_NOT_EXIST.unprocessableEntity()
        }
    }
}
