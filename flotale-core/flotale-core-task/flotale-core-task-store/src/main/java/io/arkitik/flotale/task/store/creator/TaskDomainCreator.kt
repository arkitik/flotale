package io.arkitik.flotale.task.store.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface TaskDomainCreator : StoreIdentityCreator<String, TaskDomain> {
    fun StageDomain.stage(): TaskDomainCreator

    fun String.taskKey(): TaskDomainCreator

    fun String.taskName(): TaskDomainCreator

    fun Boolean.terminalTask(): TaskDomainCreator

    fun TaskStatus.status(): TaskDomainCreator
}
