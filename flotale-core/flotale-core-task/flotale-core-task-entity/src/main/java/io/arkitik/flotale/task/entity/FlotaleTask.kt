package io.arkitik.flotale.task.entity

import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class FlotaleTask(
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
    @Column(
        nullable = false,
        updatable = false,
    )
    override val taskKey: String,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val taskName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    override var status: TaskStatus,
    @Column(nullable = false, updatable = false)
    override val terminalTask: Boolean,
) : TaskDomain
