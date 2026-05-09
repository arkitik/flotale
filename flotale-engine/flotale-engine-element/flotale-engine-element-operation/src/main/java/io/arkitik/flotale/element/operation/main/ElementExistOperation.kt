package io.arkitik.flotale.element.operation.main

import io.arkitik.flotale.element.sdk.dto.ElementReferenceData
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.radix.develop.operation.Operation

internal class ElementExistOperation(
    private val elementStoreQuery: ElementStoreQuery,
) : Operation<ElementReferenceData, Boolean> {
    override fun ElementReferenceData.operate(): Boolean =
        elementStoreQuery.existByElementKeyAndType(elementKey, elementType)
}
