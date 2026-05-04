package io.arkitik.flotale.element.operation.main

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.operation.errors.FlotaleElementErrors
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:27 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class FindElementByReferenceOperation(
    private val elementStoreQuery: ElementStoreQuery,
) : Operation<String, ElementDomain> {
    override fun String.operate() =
        elementStoreQuery.findByElementKey(this)
            .resourceNotFound(FlotaleElementErrors.ELEMENT_DOES_NOT_EXIST)
}
