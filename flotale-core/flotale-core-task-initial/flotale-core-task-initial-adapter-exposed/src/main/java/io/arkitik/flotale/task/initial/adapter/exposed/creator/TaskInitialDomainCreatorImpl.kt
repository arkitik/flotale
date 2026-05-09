package io.arkitik.flotale.task.initial.adapter.exposed.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.initial.domain.TaskInitialDomain
import io.arkitik.flotale.task.initial.entity.exposed.FlotaleTaskInitialExposed
import io.arkitik.flotale.task.initial.store.creator.TaskInitialDomainCreator
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class TaskInitialDomainCreatorImpl(
    private val database: Database?,
) : TaskInitialDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var stage: StageDomain
    private lateinit var task: TaskDomain

    override fun String.uuid(): TaskInitialDomainCreator {
        this@TaskInitialDomainCreatorImpl.uuid = this
        return this@TaskInitialDomainCreatorImpl
    }

    override fun StageDomain.stage(): TaskInitialDomainCreator {
        this@TaskInitialDomainCreatorImpl.stage = this
        return this@TaskInitialDomainCreatorImpl
    }

    override fun TaskDomain.task(): TaskInitialDomainCreator {
        this@TaskInitialDomainCreatorImpl.task = this
        return this@TaskInitialDomainCreatorImpl
    }

    override fun create(): TaskInitialDomain = FlotaleTaskInitialExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        stageUuid = stage.uuid,
        taskUuid = task.uuid,
        database = database,
    )
}
