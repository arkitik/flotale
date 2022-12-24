package io.arkitik.flotale.stage.operation.roles

import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.stage.operation.errors.FlotaleStageErrors
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:13 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class StageDuplicationRole(
    private val stageStoreQuery: StageStoreQuery,
) : OperationRole<String, Unit> {
    private val statuses = listOf(StageStatus.ACTIVE)

    override fun String.operateRole() {
        if (stageStoreQuery.existByKeyAndStatusIn(this, statuses)) {
            throw FlotaleStageErrors.STAGE_ALREADY_EXIST.unprocessableEntity()
        }
    }
}
