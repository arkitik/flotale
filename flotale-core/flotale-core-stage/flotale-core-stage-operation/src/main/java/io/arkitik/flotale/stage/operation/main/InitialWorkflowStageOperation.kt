package io.arkitik.flotale.stage.operation.main

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.initial.store.query.StageInitialStoreQuery
import io.arkitik.flotale.stage.operation.errors.FlotaleStageErrors
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:38 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class InitialWorkflowStageOperation(
    private val stageInitialStoreQuery: StageInitialStoreQuery,
) : Operation<WorkflowDomain, StageDomain> {
    override fun WorkflowDomain.operate(): StageDomain {
        return stageInitialStoreQuery.findByWorkflow(this)?.stage.resourceNotFound(FlotaleStageErrors.NO_INITIAL_STAGE)
    }
}
