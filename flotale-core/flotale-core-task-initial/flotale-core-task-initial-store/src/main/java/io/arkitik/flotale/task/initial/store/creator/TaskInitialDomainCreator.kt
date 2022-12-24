package io.arkitik.flotale.task.initial.store.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface TaskInitialDomainCreator : StoreIdentityCreator<String, TaskInitialDomain> {
    fun StageDomain.stage(): TaskInitialDomainCreator

    fun TaskDomain.task(): TaskInitialDomainCreator
}
