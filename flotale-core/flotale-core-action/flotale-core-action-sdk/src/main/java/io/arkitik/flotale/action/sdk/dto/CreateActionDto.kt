package io.arkitik.flotale.action.sdk.dto

import io.arkitik.flotale.task.domain.TaskDomain

data class CreateActionDto(
    val actionKey: String,
    val actionName: String,
    val sourceTask: TaskDomain,
    val destinationTask: TaskDomain,
)
