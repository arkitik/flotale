package io.arkitik.flotale.stage.operation.main

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.delete
import io.arkitik.radix.develop.store.storeUpdaterWithUpdate

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:29 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class DeleteStageOperation(
    private val stageStore: StageStore,
    private val stageInitialStore: StageInitialStore,
) : Operation<StageDomain, Unit> {
    override fun StageDomain.operate() {
        with(stageStore) {
            storeUpdaterWithUpdate(identityUpdater()) {
                StageStatus.DELETED.status()
                update()
            }
        }.let {
            stageInitialStore.storeQuery.findByStage(it)
        }?.also(stageInitialStore::delete)
    }
}
