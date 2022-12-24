package io.arkitik.flotale.stage.initial.adapter.updater

import io.arkitik.flotale.stage.initial.entity.FlotaleStageInitial
import io.arkitik.flotale.stage.initial.store.updater.StageInitialDomainUpdater

internal class StageInitialDomainUpdaterImpl(
    private val entity: FlotaleStageInitial,
) : StageInitialDomainUpdater {
    override fun update(): FlotaleStageInitial = entity
}
