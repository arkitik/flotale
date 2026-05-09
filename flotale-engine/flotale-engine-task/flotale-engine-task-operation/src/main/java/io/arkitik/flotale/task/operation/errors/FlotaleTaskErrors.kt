package io.arkitik.flotale.task.operation.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:41 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal enum class FlotaleTaskErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    TASK_ALREADY_EXIST(
        "FLOTALE-TASK-4000",
        "Task already exists"
    ),
    TASK_DOES_NOT_EXIST(
        "FLOTALE-TASK-4100",
        "Task does not exists"
    ),
    WORKFLOW_HAS_INITIAL_TASK(
        "FLOTALE-TASK-4200",
        "Workflow has already an initial task"
    ),
    NO_INITIAL_TASK(
        "FLOTALE-TASK-4300",
        "Stage does not has an initial task"
    );
}
