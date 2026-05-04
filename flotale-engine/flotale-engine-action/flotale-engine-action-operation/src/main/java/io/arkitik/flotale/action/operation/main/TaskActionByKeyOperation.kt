package io.arkitik.flotale.action.operation.main

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.operation.errors.FlotaleActionErrors
import io.arkitik.flotale.action.sdk.dto.TaskActionByKeyDto
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.shared.ext.resourceNotFound

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 10:42 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class TaskActionByKeyOperation(
    private val actionStoreQuery: ActionStoreQuery,
) : Operation<TaskActionByKeyDto, ActionDomain> {
    override fun TaskActionByKeyDto.operate() =
        actionStoreQuery.findBySourceTaskAndActionKey(
            task = task,
            key = actionKey
        ).resourceNotFound(FlotaleActionErrors.ACTION_DOES_NOT_EXIST)
}
