package io.arkitik.flotale.element.flow.adapter.exposed.updater

import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.exposed.FlotaleElementFlowExposed
import io.arkitik.flotale.element.flow.store.updater.ElementFlowDomainUpdater

internal class ElementFlowDomainUpdaterImpl(
    private val entity: FlotaleElementFlowExposed,
) : ElementFlowDomainUpdater {

    override fun update(): ElementFlowDomain = entity
}
