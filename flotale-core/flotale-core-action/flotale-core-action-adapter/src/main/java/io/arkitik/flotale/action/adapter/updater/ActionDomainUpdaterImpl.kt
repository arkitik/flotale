package io.arkitik.flotale.action.adapter.updater

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.action.store.updater.ActionDomainUpdater

internal class ActionDomainUpdaterImpl(
    private val entity: FlotaleAction,
) : ActionDomainUpdater {
    override fun ActionStatus.status(): ActionDomainUpdater {
        entity.status = this
        return this@ActionDomainUpdaterImpl
    }

    override fun update() = entity
}
