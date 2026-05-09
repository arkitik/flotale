package io.arkitik.flotale.stage.adapter.exposed

import io.arkitik.flotale.stage.adapter.exposed.creator.StageDomainCreatorImpl
import io.arkitik.flotale.stage.adapter.exposed.query.ExposedStageStoreQuery
import io.arkitik.flotale.stage.adapter.exposed.updater.StageDomainUpdaterImpl
import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageExposed
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.flotale.stage.store.creator.StageDomainCreator
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.flotale.stage.store.updater.StageDomainUpdater
import io.arkitik.radix.adapter.exposed.ExposedStore
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database

class ExposedStageStore(
    database: Database?,
) : ExposedStore<String, StageDomain, FlotaleStageTable>(
    identityTable = FlotaleStageTable,
    database = database,
), StageStore {

    override val storeQuery: StageStoreQuery = ExposedStageStoreQuery(database)

    override fun identityCreator(): StageDomainCreator = StageDomainCreatorImpl(database)

    override fun StageDomain.identityUpdater(): StageDomainUpdater =
        StageDomainUpdaterImpl(this as FlotaleStageExposed)

    override fun <K : Any> UpdateBuilder<K>.createEntity(identity: StageDomain) {
        identity as FlotaleStageExposed
        this[identityTable.workflow] = identity.workflowUuid
        this[identityTable.stageKey] = identity.stageKey
        this[identityTable.stageName] = identity.stageName
        this[identityTable.status] = identity.status
    }

    override fun <K : Any> UpdateBuilder<K>.updateEntity(identity: StageDomain) {
        identity as FlotaleStageExposed
        this[identityTable.status] = identity.status
    }
}
