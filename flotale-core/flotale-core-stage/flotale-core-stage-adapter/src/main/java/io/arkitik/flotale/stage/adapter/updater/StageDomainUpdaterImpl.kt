package io.arkitik.flotale.stage.adapter.updater

import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.stage.store.updater.StageDomainUpdater

internal class StageDomainUpdaterImpl(
    private val entity: FlotaleStage,
) : StageDomainUpdater {
    override fun StageStatus.status(): StageDomainUpdater {
        entity.status = this
        return this@StageDomainUpdaterImpl
    }

    override fun update(): FlotaleStage = entity
}
