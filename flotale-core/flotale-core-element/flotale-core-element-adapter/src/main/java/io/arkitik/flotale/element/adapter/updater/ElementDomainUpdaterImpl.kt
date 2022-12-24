package io.arkitik.flotale.element.adapter.updater

import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.flotale.element.store.updater.ElementDomainUpdater
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.FlotaleTask

internal class ElementDomainUpdaterImpl(
    private val entity: FlotaleElement,
) : ElementDomainUpdater {
    override fun TaskDomain.task(): ElementDomainUpdater {
        entity.task = this as FlotaleTask
        return this@ElementDomainUpdaterImpl
    }

    override fun update(): FlotaleElement = entity
}
