package io.arkitik.flotale.element.store.creator

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface ElementDomainCreator : StoreIdentityCreator<String, ElementDomain> {
    fun String.elementKey(): ElementDomainCreator

    fun TaskDomain.task(): ElementDomainCreator
    fun String.addedBy(): ElementDomainCreator
}
