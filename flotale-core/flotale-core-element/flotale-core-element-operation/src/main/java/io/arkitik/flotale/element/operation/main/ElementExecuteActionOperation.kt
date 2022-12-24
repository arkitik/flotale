package io.arkitik.flotale.element.operation.main

import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.sdk.dto.ElementActionDto
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.creatorWithSave
import io.arkitik.radix.develop.store.storeUpdaterWithSave

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:52 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class ElementExecuteActionOperation(
    private val elementStore: ElementStore,
    private val elementFlowStore: ElementFlowStore,
) : Operation<ElementActionDto, Unit> {
    override fun ElementActionDto.operate() {
        with(elementStore) {
            storeUpdaterWithSave(element.identityUpdater()) {
                action.destinationTask.task()
                update()
            }
        }.also {
            with(elementFlowStore) {
                creatorWithSave(identityCreator()) {
                    it.element()
                    action.action()
                    executedBy.executedBy()
                }
            }
        }
    }
}
