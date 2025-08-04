package io.arkitik.flotale.stage.entity

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class FlotaleStage(
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
    @ManyToOne(
        optional = false,
        fetch = FetchType.EAGER,
    )
    override val workflow: FlotaleWorkflow,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val stageKey: String,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val stageName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    override var status: StageStatus,
) : StageDomain
