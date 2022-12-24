package io.arkitik.flotale.element.entity

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.task.entity.FlotaleTask
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class FlotaleElement(
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
    override val elementKey: String,
    @ManyToOne(
        optional = false,
        fetch = FetchType.EAGER,
    )
    override var task: FlotaleTask,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val addedBy: String,
) : ElementDomain
