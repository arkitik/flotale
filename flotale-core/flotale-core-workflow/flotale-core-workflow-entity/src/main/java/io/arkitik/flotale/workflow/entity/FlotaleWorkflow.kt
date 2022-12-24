package io.arkitik.flotale.workflow.entity

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
class FlotaleWorkflow(
    @Column(
        nullable = false,
        updatable = false,
    )
    @Id
    override val uuid: String,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val creationDate: LocalDateTime,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val workflowKey: String,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val workflowName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    override var status: WorkflowStatus,
) : WorkflowDomain
