package io.arkitik.flotale.task.operation.main

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.operation.errors.FlotaleTaskErrors
import io.arkitik.flotale.task.store.query.TaskStoreQuery
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:14 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class FindTaskOperation(
    private val taskStoreQuery: TaskStoreQuery,
) : Operation<String, TaskDomain> {
    override fun String.operate() =
        taskStoreQuery.findByKeyAndNotDeleted(this)
            .resourceNotFound(FlotaleTaskErrors.TASK_DOES_NOT_EXIST)
}
