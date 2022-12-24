package io.arkitik.flotale.task.operation.roles

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.initial.store.query.TaskInitialStoreQuery
import io.arkitik.flotale.task.operation.errors.FlotaleTaskErrors
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.notAcceptable

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:11 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class StageShouldNotHasAnotherInitialTask(
    private val taskInitialStoreQuery: TaskInitialStoreQuery,
) : OperationRole<StageDomain, Unit> {
    override fun StageDomain.operateRole() {
        if (taskInitialStoreQuery.existByStage(this)) {
            throw FlotaleTaskErrors.WORKFLOW_HAS_INITIAL_TASK.notAcceptable()
        }
    }
}
