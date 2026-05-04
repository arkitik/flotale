package io.arkitik.flotale.stage.sdk.dto

import io.arkitik.flotale.workflow.domain.WorkflowDomain

class CreateStageDto(
    val workflow: WorkflowDomain,
    val stageKey: String,
    val stageName: String,
    val initialStage: Boolean,
)
