package io.arkitik.flotale.stage.initial.domain

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.identity.Identity

interface StageInitialDomain : Identity<String> {
    override val uuid: String

    val workflow: WorkflowDomain

    val stage: StageDomain
}
