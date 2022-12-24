package io.arkitik.flotale.stage.store.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.store.query.StoreQuery

interface StageStoreQuery : StoreQuery<String, StageDomain> {
    fun existByKeyAndStatusIn(
        key: String,
        statuses: List<StageStatus>,
    ): Boolean

    fun findByKeyAndNotDeleted(
        key: String,
    ): StageDomain?

    fun allWorkflowStages(workflow: WorkflowDomain): List<StageDomain>
}
