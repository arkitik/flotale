package io.arkitik.flotale.stage.adapter.exposed.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageExposed
import io.arkitik.flotale.stage.store.creator.StageDomainCreator
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class StageDomainCreatorImpl(
    private val database: Database?,
) : StageDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var workflow: WorkflowDomain
    private lateinit var stageKey: String
    private lateinit var stageName: String
    private lateinit var status: StageStatus

    override fun String.uuid(): StageDomainCreator {
        this@StageDomainCreatorImpl.uuid = this
        return this@StageDomainCreatorImpl
    }

    override fun WorkflowDomain.workflow(): StageDomainCreator {
        this@StageDomainCreatorImpl.workflow = this
        return this@StageDomainCreatorImpl
    }

    override fun String.stageKey(): StageDomainCreator {
        this@StageDomainCreatorImpl.stageKey = this
        return this@StageDomainCreatorImpl
    }

    override fun String.stageName(): StageDomainCreator {
        this@StageDomainCreatorImpl.stageName = this
        return this@StageDomainCreatorImpl
    }

    override fun StageStatus.status(): StageDomainCreator {
        this@StageDomainCreatorImpl.status = this
        return this@StageDomainCreatorImpl
    }

    override fun create(): StageDomain = FlotaleStageExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        workflowUuid = workflow.uuid,
        stageKey = stageKey,
        stageName = stageName,
        status = status,
        database = database,
    )
}
