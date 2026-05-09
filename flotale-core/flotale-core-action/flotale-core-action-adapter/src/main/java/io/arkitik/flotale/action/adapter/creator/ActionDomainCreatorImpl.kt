package io.arkitik.flotale.action.adapter.creator

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.domain.embedded.ActionType
import io.arkitik.flotale.action.entity.FlotaleAction
import io.arkitik.flotale.action.store.creator.ActionDomainCreator
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.FlotaleTask
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

    private var actionMessage: String? = null

    private lateinit var actionColor: String

    private var actionHint: String? = null

    private var actionOutlined: Boolean = false

    private var successExecutionMessage: String? = null

    private var failedExecutionMessage: String? = null

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

    override fun ActionStatus.actionStatus(): ActionDomainCreator {
        status = this
        return this@ActionDomainCreatorImpl
    }

    override fun String?.actionMessage(): ActionDomainCreator {
        actionMessage = this
        return this@ActionDomainCreatorImpl
    }

    override fun String.actionColor(): ActionDomainCreator {
        actionColor = this
        return this@ActionDomainCreatorImpl
    }

    override fun String?.actionHint(): ActionDomainCreator {
        actionHint = this
        return this@ActionDomainCreatorImpl
    }

    override fun Boolean.actionOutlined(): ActionDomainCreator {
        actionOutlined = this
        return this@ActionDomainCreatorImpl
    }

    override fun String?.successExecutionMessage(): ActionDomainCreator {
        successExecutionMessage = this
        return this@ActionDomainCreatorImpl
    }

    override fun String?.failedExecutionMessage(): ActionDomainCreator {
        failedExecutionMessage = this
        return this@ActionDomainCreatorImpl
    }

    override fun create(): FlotaleAction = FlotaleAction(
        sourceTask = sourceTask as FlotaleTask,
        destinationTask = destinationTask as FlotaleTask,
        actionKey = actionKey,
        actionName = actionName,
        actionType = actionType,
        actionStatus = status,
        actionMessage = actionMessage,
        actionColor = actionColor,
        actionHint = actionHint,
        actionOutlined = actionOutlined,
        successExecutionMessage = successExecutionMessage,
        failedExecutionMessage = failedExecutionMessage,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
    )
}
