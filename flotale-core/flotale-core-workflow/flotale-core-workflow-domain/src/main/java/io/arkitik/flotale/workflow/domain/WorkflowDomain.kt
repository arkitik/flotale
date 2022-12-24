package io.arkitik.flotale.workflow.domain

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.radix.develop.identity.Identity

interface WorkflowDomain : Identity<String> {
    override val uuid: String

    val workflowKey: String

    val workflowName: String

    val status: WorkflowStatus
}
