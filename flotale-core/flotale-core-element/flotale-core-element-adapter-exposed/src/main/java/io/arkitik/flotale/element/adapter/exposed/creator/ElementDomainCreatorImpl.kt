package io.arkitik.flotale.element.adapter.exposed.creator

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.exposed.FlotaleElementExposed
import io.arkitik.flotale.element.store.creator.ElementDomainCreator
import io.arkitik.flotale.task.domain.TaskDomain
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class ElementDomainCreatorImpl(
    private val database: Database?,
) : ElementDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var elementKey: String
    private lateinit var task: TaskDomain
    private lateinit var addedBy: String

    override fun String.uuid(): ElementDomainCreator {
        this@ElementDomainCreatorImpl.uuid = this
        return this@ElementDomainCreatorImpl
    }

    override fun String.elementKey(): ElementDomainCreator {
        this@ElementDomainCreatorImpl.elementKey = this
        return this@ElementDomainCreatorImpl
    }

    override fun TaskDomain.task(): ElementDomainCreator {
        this@ElementDomainCreatorImpl.task = this
        return this@ElementDomainCreatorImpl
    }

    override fun String.addedBy(): ElementDomainCreator {
        this@ElementDomainCreatorImpl.addedBy = this
        return this@ElementDomainCreatorImpl
    }

    override fun create(): ElementDomain = FlotaleElementExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        elementKey = elementKey,
        taskUuid = task.uuid,
        addedBy = addedBy,
        database = database,
    )
}
