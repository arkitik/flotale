package io.arkitik.flotale.element.entity

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.task.entity.FlotaleTask
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = "flotale_element_unique",
            columnNames = ["elementKey", "elementType"],
        )
    ],
)
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
    @Column(
        nullable = false,
        updatable = false,
    )
    override val elementType: String,
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
