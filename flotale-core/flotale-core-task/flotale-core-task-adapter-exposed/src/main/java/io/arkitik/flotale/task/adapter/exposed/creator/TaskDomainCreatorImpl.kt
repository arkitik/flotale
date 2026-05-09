package io.arkitik.flotale.task.adapter.exposed.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskExposed
import io.arkitik.flotale.task.store.creator.TaskDomainCreator
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class TaskDomainCreatorImpl(
    private val database: Database?,
) : TaskDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var stage: StageDomain
    private lateinit var taskKey: String
    private lateinit var taskName: String
    private var terminalTask: Boolean = false
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

    override fun create(): TaskDomain = FlotaleTaskExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        stageUuid = stage.uuid,
        taskKey = taskKey,
        taskName = taskName,
        terminalTask = terminalTask,
        status = status,
        database = database,
    )
}
