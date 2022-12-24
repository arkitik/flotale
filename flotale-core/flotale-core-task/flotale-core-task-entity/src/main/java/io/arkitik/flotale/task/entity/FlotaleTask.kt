package io.arkitik.flotale.task.entity

import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

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
) : TaskDomain
