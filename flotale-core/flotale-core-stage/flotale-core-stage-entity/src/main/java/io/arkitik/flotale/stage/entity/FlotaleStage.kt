package io.arkitik.flotale.stage.entity

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

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
