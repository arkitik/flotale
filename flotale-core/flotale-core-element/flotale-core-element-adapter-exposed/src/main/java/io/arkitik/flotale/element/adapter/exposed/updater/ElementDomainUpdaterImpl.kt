package io.arkitik.flotale.element.adapter.exposed.updater

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.exposed.FlotaleElementExposed
import io.arkitik.flotale.element.store.updater.ElementDomainUpdater
import io.arkitik.flotale.task.domain.TaskDomain

internal class ElementDomainUpdaterImpl(
    private val entity: FlotaleElementExposed,
) : ElementDomainUpdater {

    override fun TaskDomain.task(): ElementDomainUpdater {
        entity.task = this
        return this@ElementDomainUpdaterImpl
    }

    override fun update(): ElementDomain = entity
}
