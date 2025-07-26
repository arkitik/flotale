package io.arkitik.flotale.engine.operation.workflow

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.action.sdk.dto.TaskActionByKeyDto
import io.arkitik.flotale.element.sdk.FlotaleElementDomainSdk
import io.arkitik.flotale.element.sdk.dto.CreateElementDto
import io.arkitik.flotale.element.sdk.dto.ElementActionDto
import io.arkitik.flotale.engine.core.FlotaleWorkflowEngine
import io.arkitik.flotale.engine.core.dto.ElementDetails
import io.arkitik.flotale.engine.core.dto.ReferenceData
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.action.ActionExecutor
import io.arkitik.flotale.engine.function.task.ElementTaskBroadcaster
import io.arkitik.flotale.engine.operation.errors.EngineErrors
import io.arkitik.flotale.stage.sdk.FlotaleStageDomainSdk
import io.arkitik.flotale.task.sdk.FlotaleTaskDomainSdk
import io.arkitik.flotale.workflow.sdk.FlotaleWorkflowDomainSdk
import io.arkitik.radix.develop.operation.ext.runOperation
import io.arkitik.radix.develop.shared.ext.unprocessableEntity
import org.slf4j.LoggerFactory

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:35 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleWorkflowEngineImpl(
    private val flotaleWorkflowDomainSdk: FlotaleWorkflowDomainSdk,
    private val flotaleStageDomainSdk: FlotaleStageDomainSdk,
    private val flotaleTaskDomainSdk: FlotaleTaskDomainSdk,
    private val flotaleActionDomainSdk: FlotaleActionDomainSdk,
    private val flotaleElementDomainSdk: FlotaleElementDomainSdk,

    private val elementTaskBroadcaster: ElementTaskBroadcaster,
    private val actionExecutionValidator: ActionExecutionValidator,
    private val actionExecutor: ActionExecutor,
) : FlotaleWorkflowEngine {
    companion object {
        private val logger = LoggerFactory.getLogger(FlotaleWorkflowEngineImpl::class.java)
    }

    override fun initiateElement(workflowKey: String, elementKey: String, addedBy: String): ElementDetails {
        logger.debug("Initiating [element: {}] under [workflow: {}] by {}", elementKey, workflowKey, addedBy)
        val workflow = flotaleWorkflowDomainSdk.findWorkflow.runOperation(workflowKey)
        val initialStage = flotaleStageDomainSdk.initialWorkflowStage.runOperation(workflow)
        val initialTask = flotaleTaskDomainSdk.initialStageTask.runOperation(initialStage)

        flotaleElementDomainSdk.createElement
            .runOperation(
                CreateElementDto(
                    elementKey = elementKey,
                    task = initialTask,
                    addedBy = addedBy
                )
            )

        elementTaskBroadcaster.elementEnter(
            elementKey = elementKey,
            taskKey = initialTask.taskKey,
            executedBy = addedBy
        )
        logger.debug(
            "[Element: {}] under [workflow: {}] has been initiated successfully by {}",
            elementKey,
            workflowKey,
            addedBy
        )
        return elementDetails(
            elementKey = elementKey,
            requestedBy = addedBy
        )
    }

    override fun validateExecuteAction(actionKey: String, elementKey: String, requestedBy: String) {
        logger.debug("Validating [action: {}] for [element: {}] by {}", actionKey, elementKey, requestedBy)

        val element = flotaleElementDomainSdk.findElementByReference
            .runOperation(elementKey)
        val action = flotaleActionDomainSdk.taskActionByKey
            .runOperation(
                TaskActionByKeyDto(
                    task = element.task,
                    actionKey = actionKey
                )
            )

        actionCanBeExecuted(action = action, elementKey = elementKey, executedBy = requestedBy)
            .takeUnless { it }?.let {
                throw EngineErrors.ACTION_CANT_BE_EXECUTED.unprocessableEntity()
            }
        logger.debug(
            "Validating [action: {}] for [element: {}] by {} has been successfully verified, Action can be executed without any expected issue",
            actionKey,
            elementKey,
            requestedBy
        )
    }

    override fun executeAction(actionKey: String, elementKey: String, executedBy: String) {
        logger.debug("Executing [action: {}] for [element: {}] by {}", actionKey, elementKey, executedBy)

        runCatching {
            val element = flotaleElementDomainSdk.findElementByReference
                .runOperation(elementKey)

            val action = flotaleActionDomainSdk.taskActionByKey
                .runOperation(
                    TaskActionByKeyDto(
                        task = element.task,
                        actionKey = actionKey
                    )
                )

            actionCanBeExecuted(action = action, elementKey = elementKey, executedBy = executedBy)
                .takeUnless { it }?.let {
                    logger.error(
                        "Executing [action: {}] for [element: {}] by {} is prevented",
                        actionKey,
                        elementKey,
                        executedBy
                    )
                    throw EngineErrors.ACTION_CANT_BE_EXECUTED.unprocessableEntity()
                }

            actionExecutor.executeAction(
                actionKey = actionKey,
                elementKey = elementKey,
                executedBy = executedBy
            )

            flotaleElementDomainSdk.elementExecuteAction
                .runOperation(
                    ElementActionDto(
                        action = action,
                        element = element,
                        executedBy = executedBy
                    )
                )

            elementTaskBroadcaster.runCatching {
                elementExit(elementKey = elementKey, taskKey = action.sourceTask.taskKey, executedBy = executedBy)
                elementEnter(elementKey = elementKey, taskKey = action.destinationTask.taskKey, executedBy = executedBy)
            }.onFailure {
                logger.warn(
                    "An issue acquired while executing `TaskBroadcaster` for [element: {}] and [action: {}], [reason: {}] (Issue will be ignored)",
                    elementKey,
                    actionKey,
                    it.message
                )
            }
        }.onFailure {
            logger.error(
                "[Action: {}] for [element: {}] by {} has been failed while execution, [error: {}]",
                actionKey,
                elementKey,
                executedBy,
                it.message,
                it
            )
            throw it
        }.onSuccess {
            logger.debug(
                "[Action: {}] for [element: {}] by {} has been executed successfully",
                actionKey,
                elementKey,
                executedBy
            )
        }

    }

    override fun elementDetails(elementKey: String, requestedBy: String): ElementDetails {
        val element = flotaleElementDomainSdk.findElementByReference
            .runOperation(elementKey)
        val actionDomains = flotaleActionDomainSdk.taskActions
            .runOperation(element.task)
        return ElementDetails(
            elementKey = elementKey,
            workflow = ReferenceData(
                key = element.task.stage.workflow.workflowKey,
                name = element.task.stage.workflow.workflowName
            ),
            stage = ReferenceData(
                key = element.task.stage.stageKey,
                name = element.task.stage.stageName
            ),
            task = ReferenceData(
                key = element.task.taskKey,
                name = element.task.taskName
            ),
            actions = actionDomains.filter {
                actionCanBeExecuted(action = it, elementKey = elementKey, executedBy = requestedBy)
            }.map { actionDomain ->
                ReferenceData(
                    actionDomain.actionKey,
                    actionDomain.actionName
                )
            }
        )
    }

    private fun actionCanBeExecuted(action: ActionDomain, elementKey: String, executedBy: String) =
        actionExecutionValidator.validateExecution(
            actionKey = action.actionKey,
            elementKey = elementKey,
            requestedBy = executedBy
        )
}
