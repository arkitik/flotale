package io.arkitik.flotale.element.flow.domain

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.radix.develop.identity.Identity

interface ElementFlowDomain : Identity<String> {
    override val uuid: String

    val element: ElementDomain

    val action: ActionDomain

    val executedBy: String

    val executionData: ByteArray?
}
