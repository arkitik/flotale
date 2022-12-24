package io.arkitik.flotale.stage.initial.store

import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.stage.initial.store.creator.StageInitialDomainCreator
import io.arkitik.flotale.stage.initial.store.query.StageInitialStoreQuery
import io.arkitik.flotale.stage.initial.store.updater.StageInitialDomainUpdater
import io.arkitik.radix.develop.store.Store

interface StageInitialStore : Store<String, StageInitialDomain> {
    override val storeQuery: StageInitialStoreQuery

    override fun identityCreator(): StageInitialDomainCreator

    override fun StageInitialDomain.identityUpdater(): StageInitialDomainUpdater
}
