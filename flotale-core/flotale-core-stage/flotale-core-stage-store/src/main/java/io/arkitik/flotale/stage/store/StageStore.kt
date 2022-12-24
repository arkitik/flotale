package io.arkitik.flotale.stage.store

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.store.creator.StageDomainCreator
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.flotale.stage.store.updater.StageDomainUpdater
import io.arkitik.radix.develop.store.Store

interface StageStore : Store<String, StageDomain> {
    override val storeQuery: StageStoreQuery

    override fun identityCreator(): StageDomainCreator

    override fun StageDomain.identityUpdater(): StageDomainUpdater
}
