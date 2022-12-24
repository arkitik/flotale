package io.arkitik.flotale.action.adapter.repository

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface ActionRepository : RadixRepository<String, FlotaleAction> {
    fun existsByActionKeyAndStatusIn(
        actionKey: String,
        statuses: List<ActionStatus>,
    ): Boolean

    fun findFirstByActionKeyAndStatusNotIn(
        actionKey: String,
        statuses: List<ActionStatus>,
    ): FlotaleAction?

    fun findAllBySourceTaskAndStatus(
        sourceTask: FlotaleTask,
        status: ActionStatus,
    ): List<FlotaleAction>


    fun findBySourceTaskAndActionKeyAndStatus(
        sourceTask: FlotaleTask,
        actionKey: String,
        status: ActionStatus,
    ): FlotaleAction?
}
