package io.arkitik.flotale.element.adapter.query

import io.arkitik.flotale.element.adapter.repository.ElementRepository
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class ElementStoreQueryImpl(
    private val elementRepository: ElementRepository,
) : StoreQueryImpl<String, ElementDomain, FlotaleElement>(elementRepository), ElementStoreQuery {
    override fun existByElementKeyAndType(elementKey: String, elementType: String) =
        elementRepository.existsByElementKeyAndElementType(elementKey, elementType)

    override fun findByElementKeyAndType(elementKey: String, elementType: String) =
        elementRepository.findByElementKeyAndElementType(elementKey, elementType)
}
