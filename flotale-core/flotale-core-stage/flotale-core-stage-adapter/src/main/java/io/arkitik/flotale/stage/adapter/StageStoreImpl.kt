package io.arkitik.flotale.stage.adapter

import io.arkitik.flotale.stage.adapter.creator.StageDomainCreatorImpl
import io.arkitik.flotale.stage.adapter.query.StageStoreQueryImpl
import io.arkitik.flotale.stage.adapter.repository.StageRepository
import io.arkitik.flotale.stage.adapter.updater.StageDomainUpdaterImpl
import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.flotale.stage.store.creator.StageDomainCreator
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.flotale.stage.store.updater.StageDomainUpdater
import io.arkitik.radix.adapter.shared.StoreImpl

class StageStoreImpl(
    stageRepository: StageRepository,
) : StoreImpl<String, StageDomain, FlotaleStage>(stageRepository), StageStore {
    override val storeQuery: StageStoreQuery = StageStoreQueryImpl(stageRepository)

    override fun StageDomain.map(): FlotaleStage = this as FlotaleStage

    override fun identityCreator(): StageDomainCreator = StageDomainCreatorImpl()

    override fun StageDomain.identityUpdater(): StageDomainUpdater =
        StageDomainUpdaterImpl(map())
}
