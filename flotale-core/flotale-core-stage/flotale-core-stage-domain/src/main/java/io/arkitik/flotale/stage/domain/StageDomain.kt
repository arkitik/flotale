package io.arkitik.flotale.stage.domain

import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.identity.Identity

interface StageDomain : Identity<String> {
    override val uuid: String

    val workflow: WorkflowDomain

    val stageKey: String

    val stageName: String

    val status: StageStatus
}
