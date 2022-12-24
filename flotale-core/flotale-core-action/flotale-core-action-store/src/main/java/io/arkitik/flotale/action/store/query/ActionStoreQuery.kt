package io.arkitik.flotale.action.store.query

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.store.query.StoreQuery

interface ActionStoreQuery : StoreQuery<String, ActionDomain> {
    fun existByKeyAndStatusIn(
        key: String,
        statuses: List<ActionStatus>,
    ): Boolean

    fun findByKeyAndNotDeleted(
        key: String,
    ): ActionDomain?

    fun allTaskActions(
        task: TaskDomain,
    ): List<ActionDomain>

    fun findBySourceTaskAndActionKey(
        task: TaskDomain,
        key: String,
    ): ActionDomain?
}
