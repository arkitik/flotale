package io.arkitik.flotale.element.flow.store.creator

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface ElementFlowDomainCreator : StoreIdentityCreator<String, ElementFlowDomain> {
    fun ElementDomain.element(): ElementFlowDomainCreator
    fun ActionDomain.action(): ElementFlowDomainCreator
    fun String.executedBy(): ElementFlowDomainCreator
    fun ByteArray?.executionData(): ElementFlowDomainCreator
}
