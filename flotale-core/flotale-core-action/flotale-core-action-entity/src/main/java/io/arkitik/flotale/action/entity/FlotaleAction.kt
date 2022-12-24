package io.arkitik.flotale.action.entity

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.task.entity.FlotaleTask
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class FlotaleAction(
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
    override val sourceTask: FlotaleTask,
    @ManyToOne(
        optional = false,
        fetch = FetchType.EAGER,
    )
    override val destinationTask: FlotaleTask,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val actionKey: String,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val actionName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    override var status: ActionStatus,
) : ActionDomain
