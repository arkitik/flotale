package io.arkitik.flotale.element.flow.entity

import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

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
    @Lob
    @Column(
        nullable = true,
        updatable = false,
    )
    override val executionData: ByteArray?,
) : ElementFlowDomain
