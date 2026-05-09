package io.arkitik.flotale.task.sdk.dto

import io.arkitik.flotale.stage.domain.StageDomain

data class CreateTaskDto(
    val stage: StageDomain,
    val taskKey: String,
    val taskName: String,
    val terminal: Boolean,
    val initialTask: Boolean,
)
