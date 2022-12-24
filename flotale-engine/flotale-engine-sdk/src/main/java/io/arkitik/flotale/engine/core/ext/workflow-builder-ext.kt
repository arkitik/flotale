package io.arkitik.flotale.engine.core.ext

import io.arkitik.flotale.engine.core.dto.WorkflowData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:20 PM, 22 , **Thu, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleWorkflowsBuilder {
    private val workflows = mutableListOf<WorkflowData>()

    fun workflow(
        workflowDataBuilder: WorkflowDataBuilder.() -> Unit,
    ): FlotaleWorkflowsBuilder {
        workflows.add(WorkflowDataBuilder().apply(workflowDataBuilder).build())
        return this
    }

    fun build(): List<WorkflowData> = workflows
}
