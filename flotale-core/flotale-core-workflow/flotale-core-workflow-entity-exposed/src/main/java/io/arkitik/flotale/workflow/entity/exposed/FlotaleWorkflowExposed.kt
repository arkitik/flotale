package io.arkitik.flotale.workflow.entity.exposed

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import java.time.LocalDateTime

class FlotaleWorkflowExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    override val workflowKey: String,
    override val workflowName: String,
    override var status: WorkflowStatus,
) : WorkflowDomain
