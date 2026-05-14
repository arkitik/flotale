package io.arkitik.flotale.element.operation.main

import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.flotale.element.sdk.dto.QueryElementFlowsDto
import io.arkitik.radix.develop.operation.Operation

internal class ElementFlowsOperation(
    private val elementFlowStoreQuery: ElementFlowStoreQuery,
) : Operation<QueryElementFlowsDto, List<ElementFlowDomain>> {
    override fun QueryElementFlowsDto.operate(): List<ElementFlowDomain> =
        elementFlowStoreQuery.findAllByElement(
            elementKey = elementReference.elementKey,
            elementType = elementReference.elementType,
            ascending = ascending
        )
}
