package io.arkitik.flotale.element.adapter.creator

import io.arkitik.flotale.element.entity.FlotaleElement
import io.arkitik.flotale.element.store.creator.ElementDomainCreator
import io.arkitik.flotale.task.domain.TaskDomain
import java.time.LocalDateTime
import java.util.*

internal class ElementDomainCreatorImpl : ElementDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var elementKey: String
    private lateinit var addedBy: String

    private lateinit var task: TaskDomain

    override fun String.uuid(): ElementDomainCreator {
        uuid = this
        return this@ElementDomainCreatorImpl
    }

    override fun String.elementKey(): ElementDomainCreator {
        elementKey = this
        return this@ElementDomainCreatorImpl
    }

    override fun TaskDomain.task(): ElementDomainCreator {
        task = this
        return this@ElementDomainCreatorImpl
    }

    override fun String.addedBy(): ElementDomainCreator {
        addedBy = this
        return this@ElementDomainCreatorImpl
    }

    override fun create(): FlotaleElement = FlotaleElement(
        elementKey = elementKey,
        task = task as io.arkitik.flotale.task.entity.FlotaleTask,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        addedBy = addedBy
    )
}
