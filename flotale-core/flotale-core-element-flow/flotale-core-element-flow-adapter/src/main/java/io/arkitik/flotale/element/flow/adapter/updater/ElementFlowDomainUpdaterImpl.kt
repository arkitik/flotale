package io.arkitik.flotale.element.flow.adapter.updater

import io.arkitik.flotale.element.flow.entity.FlotaleElementFlow
import io.arkitik.flotale.element.flow.store.updater.ElementFlowDomainUpdater

internal class ElementFlowDomainUpdaterImpl(
    private val entity: FlotaleElementFlow,
) : ElementFlowDomainUpdater {
    override fun update(): FlotaleElementFlow = entity
}
