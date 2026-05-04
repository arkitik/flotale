package io.arkitik.flotale.task.operation.main

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.initial.store.query.TaskInitialStoreQuery
import io.arkitik.flotale.task.operation.errors.FlotaleTaskErrors
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:35 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class InitialStageTaskOperation(
    private val taskInitialStoreQuery: TaskInitialStoreQuery,
) : Operation<StageDomain, TaskDomain> {
    override fun StageDomain.operate() =
        taskInitialStoreQuery.findByStage(this)?.task
            .resourceNotFound(FlotaleTaskErrors.NO_INITIAL_TASK)
}
