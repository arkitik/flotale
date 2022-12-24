package io.arkitik.flotale.action.operation

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.operation.main.CreateActionOperation
import io.arkitik.flotale.action.operation.main.DeleteActionOperation
import io.arkitik.flotale.action.operation.main.FindActionOperation
import io.arkitik.flotale.action.operation.main.TaskActionByKeyOperation
import io.arkitik.flotale.action.operation.main.TaskActionsOperation
import io.arkitik.flotale.action.operation.roles.ActionDuplicationRole
import io.arkitik.flotale.action.operation.roles.ActionShouldBeNotDeleted
import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.action.sdk.dto.CreateActionDto
import io.arkitik.flotale.action.sdk.dto.TaskActionByKeyDto
import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.operation.ext.operateRole
import io.arkitik.radix.develop.operation.ext.operationBuilder

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleActionDomainSdkImpl(
    actionStore: ActionStore,
) : FlotaleActionDomainSdk {
    override val createAction: Operation<CreateActionDto, Unit> =
        operationBuilder {
            install {
                ActionDuplicationRole(actionStore.storeQuery)
                    .operateRole(actionKey)
            }

            mainOperation(CreateActionOperation(actionStore))
        }

    override val findAction: Operation<String, ActionDomain> =
        operationBuilder {
            mainOperation(FindActionOperation(actionStore.storeQuery))
        }

    override val deleteAction: Operation<ActionDomain, Unit> =
        operationBuilder {
            install(ActionShouldBeNotDeleted)
            mainOperation(DeleteActionOperation(actionStore))
        }

    override val taskActions: Operation<TaskDomain, List<ActionDomain>> =
        operationBuilder {
            mainOperation(TaskActionsOperation(actionStore.storeQuery))
        }

    override val taskActionByKey: Operation<TaskActionByKeyDto, ActionDomain> =
        operationBuilder {
            mainOperation(TaskActionByKeyOperation(actionStore.storeQuery))
        }
}
