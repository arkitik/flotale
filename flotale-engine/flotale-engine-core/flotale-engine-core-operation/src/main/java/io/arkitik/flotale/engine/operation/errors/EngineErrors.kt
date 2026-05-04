package io.arkitik.flotale.engine.operation.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:51 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
enum class EngineErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    ACTION_CANT_BE_EXECUTED(
        "FLOTALE-ENGINE-1000",
        "Action can't be executed to selected element"
    ),
}
