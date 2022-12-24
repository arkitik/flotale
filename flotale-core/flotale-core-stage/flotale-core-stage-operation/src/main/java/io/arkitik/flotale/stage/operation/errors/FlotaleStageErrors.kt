package io.arkitik.flotale.stage.operation.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:41 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal enum class FlotaleStageErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    STAGE_ALREADY_EXIST(
        "FLOTALE-STAGE-4000",
        "Stage already exists"
    ),
    STAGE_DOES_NOT_EXIST(
        "FLOTALE-STAGE-4100",
        "Stage does not exists"
    ),
    WORKFLOW_HAS_INITIAL_STAGE(
        "FLOTALE-STAGE-4200",
        "Workflow has already an initial stage"
    ),
    NO_INITIAL_STAGE(
        "FLOTALE-STAGE-4300",
        "Workflow does not has an initial stage"
    );
}
