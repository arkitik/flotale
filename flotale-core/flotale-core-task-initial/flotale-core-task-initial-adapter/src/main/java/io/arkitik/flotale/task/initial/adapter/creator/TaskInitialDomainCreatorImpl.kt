package io.arkitik.flotale.task.initial.adapter.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.initial.entity.FlotaleTaskInitial
import io.arkitik.flotale.task.initial.store.creator.TaskInitialDomainCreator
import java.time.LocalDateTime
import java.util.*

internal class TaskInitialDomainCreatorImpl : TaskInitialDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var stage: StageDomain

    private lateinit var task: TaskDomain

    override fun String.uuid(): TaskInitialDomainCreator {
        uuid = this
        return this@TaskInitialDomainCreatorImpl
    }

    override fun StageDomain.stage(): TaskInitialDomainCreator {
        stage = this
        return this@TaskInitialDomainCreatorImpl
    }

    override fun TaskDomain.task(): TaskInitialDomainCreator {
        task = this
        return this@TaskInitialDomainCreatorImpl
    }

    override fun create(): FlotaleTaskInitial = FlotaleTaskInitial(
        stage = stage as FlotaleStage,
        task = task as FlotaleTask,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
    )
}
