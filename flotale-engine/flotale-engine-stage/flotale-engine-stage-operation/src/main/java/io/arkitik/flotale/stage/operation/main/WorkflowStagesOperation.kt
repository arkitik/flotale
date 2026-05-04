package io.arkitik.flotale.stage.operation.main

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.store.query.StageStoreQuery
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.operation.Operation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 8:50 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class WorkflowStagesOperation(
    private val stageStoreQuery: StageStoreQuery,
) : Operation<WorkflowDomain, List<StageDomain>> {
    override fun WorkflowDomain.operate() =
        stageStoreQuery.allWorkflowStagesAndActive(this)
}
