package io.arkitik.flotale.action.operation.main

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.operation.Operation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 8:50 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class TaskActionsOperation(
    private val actionStoreQuery: ActionStoreQuery,
) : Operation<TaskDomain, List<ActionDomain>> {
    override fun TaskDomain.operate() =
        actionStoreQuery.allTaskActions(this)
}
