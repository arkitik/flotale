package io.arkitik.flotale.stage.operation.roles

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.operation.errors.FlotaleStageErrors
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:40 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal object StageShouldBeNotDeleted : OperationRole<StageDomain, Unit> {
    private val stageStatuses = listOf(StageStatus.ACTIVE)

    override fun StageDomain.operateRole() {
        if (!stageStatuses.contains(status)) {
            throw FlotaleStageErrors.STAGE_DOES_NOT_EXIST.unprocessableEntity()
        }
    }
}
