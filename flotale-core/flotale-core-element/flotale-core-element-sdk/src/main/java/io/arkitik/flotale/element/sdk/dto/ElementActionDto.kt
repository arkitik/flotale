package io.arkitik.flotale.element.sdk.dto

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.element.domain.ElementDomain

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:56 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class ElementActionDto(
    val action: ActionDomain,
    val element: ElementDomain,
    val executedBy: String,
)
