package io.arkitik.flotale.action.adapter.query

import io.arkitik.flotale.action.adapter.repository.ActionRepository
import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class ActionStoreQueryImpl(
    private val actionRepository: ActionRepository,
) : StoreQueryImpl<String, ActionDomain, FlotaleAction>(actionRepository), ActionStoreQuery {
    override fun existByKeyAndStatusIn(key: String, statuses: List<ActionStatus>) =
        actionRepository.existsByActionKeyAndStatusIn(key, statuses)

    override fun findByKeyAndNotDeleted(key: String) =
        actionRepository.findFirstByActionKeyAndStatusNotIn(key, listOf(ActionStatus.DELETED))

    override fun allTaskActions(task: TaskDomain) =
        actionRepository.findAllBySourceTaskAndStatus(task as FlotaleTask, ActionStatus.ACTIVE)

    override fun findBySourceTaskAndActionKey(task: TaskDomain, key: String) =
        actionRepository.findBySourceTaskAndActionKeyAndStatus(
            sourceTask = task as FlotaleTask,
            actionKey = key,
            status = ActionStatus.ACTIVE
        )
}
