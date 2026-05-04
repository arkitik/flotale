package io.arkitik.flotale.action.operation.main

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.operation.errors.FlotaleActionErrors
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:14 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class FindActionOperation(
    private val actionStoreQuery: ActionStoreQuery,
) : Operation<String, ActionDomain> {
    override fun String.operate() =
        actionStoreQuery.findByKeyAndNotDeleted(this)
            .resourceNotFound(FlotaleActionErrors.ACTION_DOES_NOT_EXIST)
}
