package io.arkitik.flotale.stage.initial.adapter.exposed.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.stage.initial.entity.exposed.FlotaleStageInitialExposed
import io.arkitik.flotale.stage.initial.store.creator.StageInitialDomainCreator
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class StageInitialDomainCreatorImpl(
    private val database: Database?,
) : StageInitialDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var workflow: WorkflowDomain
    private lateinit var stage: StageDomain

    override fun String.uuid(): StageInitialDomainCreator {
        this@StageInitialDomainCreatorImpl.uuid = this
        return this@StageInitialDomainCreatorImpl
    }

    override fun WorkflowDomain.workflow(): StageInitialDomainCreator {
        this@StageInitialDomainCreatorImpl.workflow = this
        return this@StageInitialDomainCreatorImpl
    }

    override fun StageDomain.stage(): StageInitialDomainCreator {
        this@StageInitialDomainCreatorImpl.stage = this
        return this@StageInitialDomainCreatorImpl
    }

    override fun create(): StageInitialDomain = FlotaleStageInitialExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        workflowUuid = workflow.uuid,
        stageUuid = stage.uuid,
        database = database,
    )
}
