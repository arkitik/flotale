package io.arkitik.flotale.stage.operation.main

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.operation.errors.FlotaleStageErrors
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:14 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class FindStageOperation(
    private val stageStoreQuery: StageStoreQuery,
) : Operation<String, StageDomain> {
    override fun String.operate() =
        stageStoreQuery.findByKeyAndNotDeleted(this)
            .resourceNotFound(FlotaleStageErrors.STAGE_DOES_NOT_EXIST)
}
