package io.arkitik.flotale.action.domain

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.domain.embedded.ActionType
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.identity.Identity

interface ActionDomain : Identity<String> {
    override val uuid: String

    val sourceTask: TaskDomain

    val destinationTask: TaskDomain

    val actionKey: String

    val actionName: String

    val actionType: ActionType

    val status: ActionStatus
}
