package io.arkitik.flotale.action.sdk.dto

import io.arkitik.flotale.task.domain.TaskDomain

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:53 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class TaskActionByKeyDto(
    val task: TaskDomain,
    val actionKey: String,
)
