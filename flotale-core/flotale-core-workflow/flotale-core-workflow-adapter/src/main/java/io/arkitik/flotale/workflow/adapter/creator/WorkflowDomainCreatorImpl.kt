package io.arkitik.flotale.workflow.adapter.creator

import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.flotale.workflow.store.creator.WorkflowDomainCreator
import java.time.LocalDateTime
import java.util.*

internal class WorkflowDomainCreatorImpl : WorkflowDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var workflowKey: String

    private lateinit var workflowName: String

    private lateinit var status: WorkflowStatus

    override fun String.uuid(): WorkflowDomainCreator {
        uuid = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun String.workflowKey(): WorkflowDomainCreator {
        workflowKey = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun String.workflowName(): WorkflowDomainCreator {
        workflowName = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun WorkflowStatus.status(): WorkflowDomainCreator {
        status = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun create(): FlotaleWorkflow = FlotaleWorkflow(
        workflowKey = workflowKey,
        workflowName = workflowName,
        status = status,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
    )
}
