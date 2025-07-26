package io.arkitik.flotale.task.adapter.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.store.creator.TaskDomainCreator
import java.time.LocalDateTime
import java.util.*
import kotlin.properties.Delegates

internal class TaskDomainCreatorImpl : TaskDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var stage: StageDomain

    private lateinit var taskKey: String

    private lateinit var taskName: String

    private var terminalTask by Delegates.notNull<Boolean>()

    private lateinit var status: TaskStatus

    override fun String.uuid(): TaskDomainCreator {
        this@TaskDomainCreatorImpl.uuid = this
        return this@TaskDomainCreatorImpl
    }

    override fun StageDomain.stage(): TaskDomainCreator {
        this@TaskDomainCreatorImpl.stage = this
        return this@TaskDomainCreatorImpl
    }

    override fun String.taskKey(): TaskDomainCreator {
        this@TaskDomainCreatorImpl.taskKey = this
        return this@TaskDomainCreatorImpl
    }

    override fun String.taskName(): TaskDomainCreator {
        this@TaskDomainCreatorImpl.taskName = this
        return this@TaskDomainCreatorImpl
    }

    override fun Boolean.terminalTask(): TaskDomainCreator {
        this@TaskDomainCreatorImpl.terminalTask = this
        return this@TaskDomainCreatorImpl
    }

    override fun TaskStatus.status(): TaskDomainCreator {
        this@TaskDomainCreatorImpl.status = this
        return this@TaskDomainCreatorImpl
    }

    override fun create(): FlotaleTask = FlotaleTask(
        stage = stage as io.arkitik.flotale.stage.entity.FlotaleStage,
        taskKey = taskKey,
        taskName = taskName,
        terminalTask = terminalTask,
        status = status,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
    )
}
