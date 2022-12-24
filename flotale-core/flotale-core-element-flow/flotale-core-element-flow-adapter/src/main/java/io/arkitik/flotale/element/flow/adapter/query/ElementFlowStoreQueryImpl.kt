package io.arkitik.flotale.element.flow.adapter.query

import io.arkitik.flotale.element.flow.adapter.repository.ElementFlowRepository
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.FlotaleElementFlow
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.radix.adapter.shared.query.StoreQueryImpl

internal class ElementFlowStoreQueryImpl(
    elementFlowRepository: ElementFlowRepository,
) : StoreQueryImpl<String, ElementFlowDomain, FlotaleElementFlow>(elementFlowRepository),
    ElementFlowStoreQuery
