package io.arkitik.flotale.action.adapter.creator

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.domain.embedded.ActionType
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.action.store.creator.ActionDomainCreator
import io.arkitik.flotale.task.domain.TaskDomain
import java.time.LocalDateTime
import java.util.*

internal class ActionDomainCreatorImpl : ActionDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var sourceTask: TaskDomain

    private lateinit var destinationTask: TaskDomain

    private lateinit var actionKey: String

    private lateinit var actionName: String

    private lateinit var actionType: ActionType

    private lateinit var status: ActionStatus

    override fun String.uuid(): ActionDomainCreator {
        uuid = this
        return this@ActionDomainCreatorImpl
    }

    override fun TaskDomain.sourceTask(): ActionDomainCreator {
        sourceTask = this
        return this@ActionDomainCreatorImpl
    }

    override fun TaskDomain.destinationTask(): ActionDomainCreator {
        destinationTask = this
        return this@ActionDomainCreatorImpl
    }

    override fun String.actionKey(): ActionDomainCreator {
        actionKey = this
        return this@ActionDomainCreatorImpl
    }

    override fun String.actionName(): ActionDomainCreator {
        actionName = this
        return this@ActionDomainCreatorImpl
    }

    override fun ActionType.actionType(): ActionDomainCreator {
        actionType = this
        return this@ActionDomainCreatorImpl
    }

    override fun ActionStatus.status(): ActionDomainCreator {
        status = this
        return this@ActionDomainCreatorImpl
    }

    override fun create(): FlotaleAction = FlotaleAction(
        sourceTask = sourceTask as
                io.arkitik.flotale.task.entity.FlotaleTask,
        destinationTask = destinationTask as io.arkitik.flotale.task.entity.FlotaleTask,
        actionKey = actionKey,
        actionName = actionName,
        actionType = actionType,
        status = status,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
    )
}
