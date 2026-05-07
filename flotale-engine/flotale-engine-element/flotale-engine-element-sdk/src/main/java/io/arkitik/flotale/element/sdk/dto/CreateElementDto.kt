package io.arkitik.flotale.element.sdk.dto

import io.arkitik.flotale.task.domain.TaskDomain

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:11 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class CreateElementDto(
    val elementReference: ElementReferenceData,
    val task: TaskDomain,
    val addedBy: String,
)
