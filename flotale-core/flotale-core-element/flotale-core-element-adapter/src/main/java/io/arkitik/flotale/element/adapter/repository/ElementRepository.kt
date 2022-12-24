package io.arkitik.flotale.element.adapter.repository

import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface ElementRepository : RadixRepository<String, FlotaleElement> {
    fun existsByElementKey(elementKey: String): Boolean
    fun findByElementKey(elementKey: String): FlotaleElement?
}
