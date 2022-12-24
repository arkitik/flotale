package io.arkitik.flotale.stage.initial.adapter.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.initial.entity.FlotaleStageInitial
import io.arkitik.flotale.stage.initial.store.creator.StageInitialDomainCreator
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import java.time.LocalDateTime
import java.util.UUID

internal class StageInitialDomainCreatorImpl : StageInitialDomainCreator {
    private var uuid: String = UUID.randomUUID().toString().replace("-", "")

    private lateinit var workflow: WorkflowDomain

    private lateinit var stage: StageDomain

    override fun String.uuid(): StageInitialDomainCreator {
        uuid = this
        return this@StageInitialDomainCreatorImpl
    }

    override fun WorkflowDomain.workflow(): StageInitialDomainCreator {
        workflow = this
        return this@StageInitialDomainCreatorImpl
    }

    override fun StageDomain.stage(): StageInitialDomainCreator {
        stage = this
        return this@StageInitialDomainCreatorImpl
    }

    override fun create(): FlotaleStageInitial = FlotaleStageInitial(
        workflow = workflow as
                io.arkitik.flotale.workflow.entity.FlotaleWorkflow,
        stage = stage as io.arkitik.flotale.stage.entity.FlotaleStage,
        uuid = uuid,
        creationDate = LocalDateTime.now(),
    )
}
