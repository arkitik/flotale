package io.arkitik.flotale.stage.initial.adapter.exposed

import io.arkitik.flotale.stage.initial.adapter.exposed.creator.StageInitialDomainCreatorImpl
import io.arkitik.flotale.stage.initial.adapter.exposed.query.ExposedStageInitialStoreQuery
import io.arkitik.flotale.stage.initial.adapter.exposed.updater.StageInitialDomainUpdaterImpl
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.stage.initial.entity.exposed.FlotaleStageInitialExposed
import io.arkitik.flotale.stage.initial.entity.exposed.FlotaleStageInitialTable
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.initial.store.creator.StageInitialDomainCreator
import io.arkitik.flotale.stage.initial.store.query.StageInitialStoreQuery
import io.arkitik.flotale.stage.initial.store.updater.StageInitialDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedStageInitialStore(
    database: Database?,
) : ExposedStore<String, StageInitialDomain, FlotaleStageInitialTable>(
    identityTable = FlotaleStageInitialTable,
    database = database,
), StageInitialStore {

    override val storeQuery: StageInitialStoreQuery = ExposedStageInitialStoreQuery(database)

    override fun identityCreator(): StageInitialDomainCreator = StageInitialDomainCreatorImpl(database)

    override fun StageInitialDomain.identityUpdater(): StageInitialDomainUpdater =
        StageInitialDomainUpdaterImpl(this as FlotaleStageInitialExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: StageInitialDomain) {
        identity as FlotaleStageInitialExposed
        this[identityTable.workflow] = identity.workflowUuid
        this[identityTable.stage] = identity.stageUuid
    }

    override fun <K : Any> UpdateBuilder<K>.updateEntity(identity: StageInitialDomain) {
        // No mutable fields
    }
}
