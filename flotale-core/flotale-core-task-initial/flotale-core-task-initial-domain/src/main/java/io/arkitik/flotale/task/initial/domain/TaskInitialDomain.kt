package io.arkitik.flotale.task.initial.domain

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.identity.Identity

interface TaskInitialDomain : Identity<String> {
    override val uuid: String

    val stage: StageDomain

    val task: TaskDomain
}
