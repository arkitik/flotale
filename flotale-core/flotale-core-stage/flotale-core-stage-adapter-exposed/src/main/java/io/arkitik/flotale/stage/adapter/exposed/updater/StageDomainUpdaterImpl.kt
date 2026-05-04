package io.arkitik.flotale.stage.adapter.exposed.updater

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageExposed
import io.arkitik.flotale.stage.store.updater.StageDomainUpdater

internal class StageDomainUpdaterImpl(
    private val entity: FlotaleStageExposed,
) : StageDomainUpdater {

    override fun StageStatus.status(): StageDomainUpdater {
        entity.status = this
        return this@StageDomainUpdaterImpl
    }

    override fun update(): StageDomain = entity
}
