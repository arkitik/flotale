package io.arkitik.flotale.element.store.query

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.radix.develop.store.query.StoreQuery

interface ElementStoreQuery : StoreQuery<String, ElementDomain> {
    fun existByElementKeyAndType(elementKey: String, elementType: String): Boolean
    fun findByElementKeyAndType(elementKey: String, elementType: String): ElementDomain?
}
