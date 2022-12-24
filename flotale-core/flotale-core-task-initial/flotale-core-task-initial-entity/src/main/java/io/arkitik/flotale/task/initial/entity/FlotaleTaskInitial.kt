package io.arkitik.flotale.task.initial.entity

import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class FlotaleTaskInitial(
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
    override val stage: FlotaleStage,
    @ManyToOne(
        optional = false,
        fetch = FetchType.EAGER,
    )
    override val task: FlotaleTask,
) : TaskInitialDomain
