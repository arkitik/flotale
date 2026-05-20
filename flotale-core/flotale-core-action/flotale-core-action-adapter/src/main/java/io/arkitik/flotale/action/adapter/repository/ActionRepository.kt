package io.arkitik.flotale.action.adapter.repository

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface ActionRepository : RadixRepository<String, FlotaleAction> {
    fun existsByActionKeyAndActionStatusIn(
        actionKey: String,
        statuses: List<ActionStatus>,
    ): Boolean

    fun findFirstByActionKeyAndActionStatusNotIn(
        actionKey: String,
        statuses: List<ActionStatus>,
    ): FlotaleAction?

    fun findAllBySourceTaskAndActionStatus(
        sourceTask: FlotaleTask,
        status: ActionStatus,
    ): List<FlotaleAction>


    fun findBySourceTaskAndActionKeyAndActionStatus(
        sourceTask: FlotaleTask,
        actionKey: String,
        status: ActionStatus,
    ): FlotaleAction?
}
