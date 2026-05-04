package io.arkitik.flotale.element.operation.main

import io.arkitik.flotale.element.sdk.dto.CreateElementDto
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.creatorWithInsert

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:16 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class InitiateElementOperation(
    private val elementStore: ElementStore,
) : Operation<CreateElementDto, Unit> {
    override fun CreateElementDto.operate() {
        with(elementStore) {
            creatorWithInsert(identityCreator()) {
                elementKey.elementKey()
                elementType.elementType()
                task.task()
                addedBy.addedBy()
            }
        }
    }
}
