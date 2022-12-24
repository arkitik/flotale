package io.arkitik.flotale.workflow.operation.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:41 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal enum class FlotaleWorkflowErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    WORKFLOW_ALREADY_EXIST(
        "FLOTALE-WORKFLOW-4000",
        "Workflow already exists"
    ),
    WORKFLOW_DOES_NOT_EXIST(
        "FLOTALE-WORKFLOW-4100",
        "Workflow does not exists"
    );
}
