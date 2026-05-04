package io.arkitik.flotale.workflow.adapter.exposed.creator

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowExposed
import io.arkitik.flotale.workflow.store.creator.WorkflowDomainCreator
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class WorkflowDomainCreatorImpl : WorkflowDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var workflowKey: String
    private lateinit var workflowName: String
    private lateinit var status: WorkflowStatus

    override fun String.uuid(): WorkflowDomainCreator {
        this@WorkflowDomainCreatorImpl.uuid = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun String.workflowKey(): WorkflowDomainCreator {
        this@WorkflowDomainCreatorImpl.workflowKey = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun String.workflowName(): WorkflowDomainCreator {
        this@WorkflowDomainCreatorImpl.workflowName = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun WorkflowStatus.status(): WorkflowDomainCreator {
        this@WorkflowDomainCreatorImpl.status = this
        return this@WorkflowDomainCreatorImpl
    }

    override fun create(): WorkflowDomain = FlotaleWorkflowExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        workflowKey = workflowKey,
        workflowName = workflowName,
        status = status,
    )
}
