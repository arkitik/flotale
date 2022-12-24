package io.arkitik.flotale.stage.operation.main

import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.sdk.dto.CreateStageDto
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.creatorWithSave

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class CreateStageOperation(
    private val stageStore: StageStore,
    private val stageInitialStore: StageInitialStore,
) : Operation<CreateStageDto, Unit> {
    override fun CreateStageDto.operate() {
        with(stageStore) {
            creatorWithSave(identityCreator()) {
                stageKey.stageKey()
                stageName.stageName()
                workflow.workflow()
                StageStatus.ACTIVE.status()
            }
        }.takeIf { initialStage }?.also {
            with(stageInitialStore) {
                creatorWithSave(identityCreator()) {
                    it.stage()
                    it.workflow.workflow()
                }
            }
        }
    }
}
