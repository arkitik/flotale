package io.arkitik.flotale.task.domain

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.radix.develop.identity.Identity

interface TaskDomain : Identity<String> {
    override val uuid: String

    val stage: StageDomain

    val taskKey: String

    val taskName: String

    val status: TaskStatus
}
