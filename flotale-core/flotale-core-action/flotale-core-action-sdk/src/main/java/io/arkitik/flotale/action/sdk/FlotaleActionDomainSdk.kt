package io.arkitik.flotale.action.sdk

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.sdk.dto.CreateActionDto
import io.arkitik.flotale.action.sdk.dto.TaskActionByKeyDto
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.operation.Operation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 12:10 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleActionDomainSdk {
    val createAction: Operation<CreateActionDto, Unit>
    val findAction: Operation<String, ActionDomain>
    val deleteAction: Operation<ActionDomain, Unit>

    val taskActions: Operation<TaskDomain, List<ActionDomain>>
    val taskActionByKey: Operation<TaskActionByKeyDto, ActionDomain>
}
