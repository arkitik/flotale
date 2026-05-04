package io.arkitik.flotale.stage.initial.adapter.exposed.updater

import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.stage.initial.entity.exposed.FlotaleStageInitialExposed
import io.arkitik.flotale.stage.initial.store.updater.StageInitialDomainUpdater

internal class StageInitialDomainUpdaterImpl(
    private val entity: FlotaleStageInitialExposed,
) : StageInitialDomainUpdater {

    override fun update(): StageInitialDomain = entity
}
