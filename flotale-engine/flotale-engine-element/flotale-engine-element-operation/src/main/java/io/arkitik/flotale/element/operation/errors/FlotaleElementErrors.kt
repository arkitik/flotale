package io.arkitik.flotale.element.operation.errors

import io.arkitik.radix.develop.shared.error.ErrorResponse

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:41 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal enum class FlotaleElementErrors(
    override val code: String?,
    override val message: String?,
) : ErrorResponse {
    ELEMENT_ALREADY_EXIST(
        "FLOTALE-ELEMENT-4000",
        "Element key already exists"
    ),
    ELEMENT_DOES_NOT_EXIST(
        "FLOTALE-ELEMENT-4100",
        "Element key does not exists"
    );
}
