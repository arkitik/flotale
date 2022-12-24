package io.arkitik.flotale.action.store.creator

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface ActionDomainCreator : StoreIdentityCreator<String, ActionDomain> {
    fun TaskDomain.sourceTask(): ActionDomainCreator

    fun TaskDomain.destinationTask(): ActionDomainCreator

    fun String.actionKey(): ActionDomainCreator

    fun String.actionName(): ActionDomainCreator

    fun ActionStatus.status(): ActionDomainCreator
}
