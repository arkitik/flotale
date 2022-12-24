package io.arkitik.flotale.element.flow.adapter.creator

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.flotale.element.flow.entity.FlotaleElementFlow
import io.arkitik.flotale.element.flow.store.creator.ElementFlowDomainCreator
import java.time.LocalDateTime
import java.util.*

internal class ElementFlowDomainCreatorImpl : ElementFlowDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var element: ElementDomain

    private lateinit var action: ActionDomain
    private lateinit var executedBy: String

    override fun String.uuid(): ElementFlowDomainCreator {
        uuid = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun ElementDomain.element(): ElementFlowDomainCreator {
        element = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun ActionDomain.action(): ElementFlowDomainCreator {
        action = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun String.executedBy(): ElementFlowDomainCreator {
        executedBy = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun create(): FlotaleElementFlow = FlotaleElementFlow(
        element = element as FlotaleElement,
        action = action as FlotaleAction,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        executedBy = executedBy
    )
}
