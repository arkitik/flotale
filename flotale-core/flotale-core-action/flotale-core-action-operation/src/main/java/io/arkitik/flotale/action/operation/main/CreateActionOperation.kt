package io.arkitik.flotale.action.operation.main

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.sdk.dto.CreateActionDto
import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.creatorWithSave

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class CreateActionOperation(
    private val actionStore: ActionStore,
) : Operation<CreateActionDto, Unit> {
    override fun CreateActionDto.operate() {
        with(actionStore) {
            creatorWithSave(identityCreator()) {
                actionKey.actionKey()
                actionName.actionName()
                sourceTask.sourceTask()
                destinationTask.destinationTask()
                ActionStatus.ACTIVE.status()
            }
        }
    }
}
