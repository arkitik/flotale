package io.arkitik.flotale.stage.operation.roles

import io.arkitik.flotale.stage.initial.store.query.StageInitialStoreQuery
import io.arkitik.flotale.stage.operation.errors.FlotaleStageErrors
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.notAcceptable

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:11 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class WorkflowShouldNotHasAnotherInitialStage(
    private val stageInitialStoreQuery: StageInitialStoreQuery,
) : OperationRole<WorkflowDomain, Unit> {
    override fun WorkflowDomain.operateRole() {
        if (stageInitialStoreQuery.existByWorkflow(this)) {
            throw FlotaleStageErrors.WORKFLOW_HAS_INITIAL_STAGE.notAcceptable()
        }
    }
}
