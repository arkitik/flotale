package io.arkitik.flotale.element.domain

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.identity.Identity

interface ElementDomain : Identity<String> {
    override val uuid: String

    val elementKey: String
    val task: TaskDomain

    val addedBy: String
}
