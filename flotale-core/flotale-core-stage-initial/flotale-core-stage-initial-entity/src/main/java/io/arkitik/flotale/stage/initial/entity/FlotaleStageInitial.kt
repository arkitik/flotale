package io.arkitik.flotale.stage.initial.entity

import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class FlotaleStageInitial(
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
    @ManyToOne(
        optional = false,
        fetch = FetchType.EAGER,
    )
    override val stage: FlotaleStage,
) : StageInitialDomain
