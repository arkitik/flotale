package io.arkitik.flotale.element.flow.adapter.exposed.query

import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.exposed.FlotaleElementFlowTable
import io.arkitik.flotale.element.flow.store.query.ElementFlowStoreQuery
import io.arkitik.radix.adapter.exposed.query.ExposedStoreQuery
import org.jetbrains.exposed.v1.jdbc.Database

internal class ExposedElementFlowStoreQuery(
    database: Database?,
) : ExposedStoreQuery<String, ElementFlowDomain, FlotaleElementFlowTable>(FlotaleElementFlowTable, database),
    ElementFlowStoreQuery
