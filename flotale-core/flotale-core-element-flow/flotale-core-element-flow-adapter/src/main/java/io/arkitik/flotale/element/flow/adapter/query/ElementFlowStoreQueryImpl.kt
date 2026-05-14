package io.arkitik.flotale.element.flow.adapter.query

import io.arkitik.flotale.element.flow.adapter.repository.ElementFlowRepository
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.FlotaleElementFlow
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

internal class ElementFlowStoreQueryImpl(
    private val elementFlowRepository: ElementFlowRepository,
) : StoreQueryImpl<String, ElementFlowDomain, FlotaleElementFlow>(elementFlowRepository),
    ElementFlowStoreQuery {

    override fun findAllByElement(
        elementKey: String,
        elementType: String,
        ascending: Boolean,
    ): List<ElementFlowDomain> =
        elementFlowRepository.findAllByElementElementKeyAndElementElementType(
            elementKey = elementKey,
            elementType = elementType,
            pageable = Pageable.unpaged(
                Sort.by(
                    if (ascending) Sort.Direction.ASC else Sort.Direction.DESC,
                    ElementFlowDomain::creationDate.name
                )
            )
        )
}
