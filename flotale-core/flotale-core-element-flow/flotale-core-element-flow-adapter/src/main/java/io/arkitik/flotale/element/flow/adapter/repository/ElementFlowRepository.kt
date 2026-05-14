package io.arkitik.flotale.element.flow.adapter.repository

import io.arkitik.flotale.element.flow.entity.FlotaleElementFlow
import io.arkitik.radix.adapter.shared.repository.RadixRepository
import org.springframework.data.domain.Pageable

interface ElementFlowRepository : RadixRepository<String, FlotaleElementFlow> {
    fun findAllByElementElementKeyAndElementElementType(
        elementKey: String,
        elementType: String,
        pageable: Pageable,
    ): List<FlotaleElementFlow>
}
