package io.arkitik.flotale.workflow.entity

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import java.time.LocalDateTime
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

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
