package io.arkitik.flotale.element.sdk

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.sdk.dto.CreateElementDto
import io.arkitik.flotale.element.sdk.dto.ElementActionDto
import io.arkitik.radix.develop.operation.Operation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:10 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleElementDomainSdk {
    val createElement: Operation<CreateElementDto, Unit>
    val findElementByReference: Operation<String, ElementDomain>

    val elementExecuteAction: Operation<ElementActionDto, Unit>
}
