package io.arkitik.flotale.engine.core.dto

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 8:39 PM, 22 , **Thu, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
data class ElementDetails(
    val elementKey: String,
    val workflow: ReferenceData,
    val stage: ReferenceData,
    val task: ReferenceData,
    val actions: List<ReferenceData>,
)

data class ReferenceData(
    val key: String,
    val name: String,
)
