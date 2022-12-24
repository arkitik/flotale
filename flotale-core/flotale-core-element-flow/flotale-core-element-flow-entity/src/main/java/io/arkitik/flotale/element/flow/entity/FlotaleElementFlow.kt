package io.arkitik.flotale.element.flow.entity

import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class FlotaleElementFlow(
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
    override val element: FlotaleElement,
    @ManyToOne(
        optional = false,
        fetch = FetchType.EAGER,
    )
    override val action: FlotaleAction,
    @Column(
        nullable = false,
        updatable = false,
    )
    override val executedBy: String,
) : ElementFlowDomain
