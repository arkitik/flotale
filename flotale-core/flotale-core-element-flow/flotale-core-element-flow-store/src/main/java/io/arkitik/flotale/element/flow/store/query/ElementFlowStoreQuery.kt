package io.arkitik.flotale.element.flow.store.query

import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.radix.develop.store.query.StoreQuery

interface ElementFlowStoreQuery : StoreQuery<String, ElementFlowDomain> {
    fun findAllByElement(
        elementKey: String,
        elementType: String,
        ascending: Boolean
    ): List<ElementFlowDomain>
}
