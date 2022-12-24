package io.arkitik.flotale.stage.initial.adapter

import io.arkitik.flotale.stage.initial.adapter.creator.StageInitialDomainCreatorImpl
import io.arkitik.flotale.stage.initial.adapter.query.StageInitialStoreQueryImpl
import io.arkitik.flotale.stage.initial.adapter.repository.StageInitialRepository
import io.arkitik.flotale.stage.initial.adapter.updater.StageInitialDomainUpdaterImpl
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.stage.initial.entity.FlotaleStageInitial
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.initial.store.creator.StageInitialDomainCreator
import io.arkitik.flotale.stage.initial.store.query.StageInitialStoreQuery
import io.arkitik.flotale.stage.initial.store.updater.StageInitialDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class StageInitialStoreImpl(
    stageInitialRepository: StageInitialRepository,
) : StoreImpl<String, StageInitialDomain, FlotaleStageInitial>(stageInitialRepository), StageInitialStore {
    override val storeQuery: StageInitialStoreQuery =
        StageInitialStoreQueryImpl(stageInitialRepository)

    override fun StageInitialDomain.map(): FlotaleStageInitial = this as FlotaleStageInitial

    override fun identityCreator(): StageInitialDomainCreator = StageInitialDomainCreatorImpl()

    override fun StageInitialDomain.identityUpdater(): StageInitialDomainUpdater =
        StageInitialDomainUpdaterImpl(map())
}
