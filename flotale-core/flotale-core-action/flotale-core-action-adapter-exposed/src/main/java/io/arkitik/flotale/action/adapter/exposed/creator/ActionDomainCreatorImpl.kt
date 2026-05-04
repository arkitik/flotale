package io.arkitik.flotale.action.adapter.exposed.creator

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.domain.embedded.ActionType
import io.arkitik.flotale.action.entity.exposed.FlotaleActionExposed
import io.arkitik.flotale.action.store.creator.ActionDomainCreator
import io.arkitik.flotale.task.domain.TaskDomain
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class ActionDomainCreatorImpl(
    private val database: Database?,
) : ActionDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var sourceTask: TaskDomain
    private lateinit var destinationTask: TaskDomain
    private lateinit var actionKey: String
    private lateinit var actionName: String
    private lateinit var actionType: ActionType
    private lateinit var status: ActionStatus

    override fun String.uuid(): ActionDomainCreator {
        this@ActionDomainCreatorImpl.uuid = this
        return this@ActionDomainCreatorImpl
    }

    override fun TaskDomain.sourceTask(): ActionDomainCreator {
        this@ActionDomainCreatorImpl.sourceTask = this
        return this@ActionDomainCreatorImpl
    }

    override fun TaskDomain.destinationTask(): ActionDomainCreator {
        this@ActionDomainCreatorImpl.destinationTask = this
        return this@ActionDomainCreatorImpl
    }

    override fun String.actionKey(): ActionDomainCreator {
        this@ActionDomainCreatorImpl.actionKey = this
        return this@ActionDomainCreatorImpl
    }

    override fun String.actionName(): ActionDomainCreator {
        this@ActionDomainCreatorImpl.actionName = this
        return this@ActionDomainCreatorImpl
    }

    override fun ActionType.actionType(): ActionDomainCreator {
        this@ActionDomainCreatorImpl.actionType = this
        return this@ActionDomainCreatorImpl
    }

    override fun ActionStatus.status(): ActionDomainCreator {
        this@ActionDomainCreatorImpl.status = this
        return this@ActionDomainCreatorImpl
    }

    override fun create(): ActionDomain = FlotaleActionExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        sourceTaskUuid = sourceTask.uuid,
        destinationTaskUuid = destinationTask.uuid,
        actionKey = actionKey,
        actionName = actionName,
        actionType = actionType,
        status = status,
        database = database,
    )
}
