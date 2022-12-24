package io.arkitik.flotale.stage.initial.store.query

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.store.query.StoreQuery

interface StageInitialStoreQuery : StoreQuery<String, StageInitialDomain> {
    fun existByWorkflow(workflow: WorkflowDomain): Boolean
    fun findByWorkflow(workflow: WorkflowDomain): StageInitialDomain?
    fun findByStage(stage: StageDomain): StageInitialDomain?
}
