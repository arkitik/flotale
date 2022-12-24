package io.arkitik.flotale.stage.adapter.creator

import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.stage.store.creator.StageDomainCreator
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import java.time.LocalDateTime
import java.util.*

internal class StageDomainCreatorImpl : StageDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var workflow: WorkflowDomain

    private lateinit var stageKey: String

    private lateinit var stageName: String

    private lateinit var status: StageStatus

    override fun String.uuid(): StageDomainCreator {
        uuid = this
        return this@StageDomainCreatorImpl
    }

    override fun WorkflowDomain.workflow(): StageDomainCreator {
        workflow = this
        return this@StageDomainCreatorImpl
    }

    override fun String.stageKey(): StageDomainCreator {
        stageKey = this
        return this@StageDomainCreatorImpl
    }

    override fun String.stageName(): StageDomainCreator {
        stageName = this
        return this@StageDomainCreatorImpl
    }

    override fun StageStatus.status(): StageDomainCreator {
        status = this
        return this@StageDomainCreatorImpl
    }

    override fun create(): FlotaleStage = FlotaleStage(
        workflow = workflow as FlotaleWorkflow,
        stageKey = stageKey,
        stageName = stageName,
        status = status,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
    )
}
