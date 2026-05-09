package io.arkitik.flotale.action.adapter.exposed.updater

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.entity.exposed.FlotaleActionExposed
import io.arkitik.flotale.action.store.updater.ActionDomainUpdater

internal class ActionDomainUpdaterImpl(
    private val entity: FlotaleActionExposed,
) : ActionDomainUpdater {

    override fun ActionStatus.actionStatus(): ActionDomainUpdater {
        entity.actionStatus = this
        return this@ActionDomainUpdaterImpl
    }

    override fun update(): ActionDomain = entity
}
