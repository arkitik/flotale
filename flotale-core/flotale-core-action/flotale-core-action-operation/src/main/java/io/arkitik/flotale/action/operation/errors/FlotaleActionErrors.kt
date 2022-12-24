package io.arkitik.flotale.action.operation.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:41 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal enum class FlotaleActionErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    ACTION_ALREADY_EXIST(
        "FLOTALE-ACTION-4000",
        "Action already exists"
    ),
    ACTION_DOES_NOT_EXIST(
        "FLOTALE-ACTION-4100",
        "Action does not exists"
    );
}
