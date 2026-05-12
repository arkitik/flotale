package io.arkitik.flotale.engine.operation.core

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionType
import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.action.sdk.dto.TaskActionByKeyDto
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.sdk.FlotaleElementDomainSdk
import io.arkitik.flotale.element.sdk.dto.CreateElementDto
import io.arkitik.flotale.element.sdk.dto.ElementActionDto
import io.arkitik.flotale.element.sdk.dto.ElementReferenceData
import io.arkitik.flotale.engine.core.FlotaleWorkflowEngine
import io.arkitik.flotale.engine.core.dto.ActionDetails
import io.arkitik.flotale.engine.core.dto.ElementDetails
import io.arkitik.flotale.engine.core.dto.ReferenceData
import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.action.ActionExecutor
import io.arkitik.flotale.engine.function.action.ActionFormProvider
import io.arkitik.flotale.engine.function.dtos.ExecuteActionData
import io.arkitik.flotale.engine.function.dtos.FormValidationResult
import io.arkitik.flotale.engine.function.task.ElementTaskBroadcaster
import io.arkitik.flotale.engine.operation.core.errors.EngineErrors
import io.arkitik.flotale.protocol.transactional.FlotaleTransactionalExecutor
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData
import io.arkitik.flotale.stage.sdk.FlotaleStageDomainSdk
import io.arkitik.flotale.task.sdk.FlotaleTaskDomainSdk
import io.arkitik.flotale.workflow.sdk.FlotaleWorkflowDomainSdk
import io.arkitik.radix.develop.operation.ext.runOperation
import io.arkitik.radix.develop.shared.ext.badRequest
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
    private val actionFormProvider: ActionFormProvider,
    private val flotaleTransactionalExecutor: FlotaleTransactionalExecutor,
) : FlotaleWorkflowEngine {
    companion object {
        private val logger = LoggerFactory.getLogger(FlotaleWorkflowEngineImpl::class.java)
    }

    override fun initiateElement(
        workflowKey: String,
        elementKey: String,
        elementType: String,
        addedBy: FlotaleUserTokenData,
    ): ElementDetails {
        return flotaleTransactionalExecutor.runOnTransaction {
            logger.debug(
                "Initiating [element: {}, type: {}] under [workflow: {}] by {}",
                elementKey,
                elementType,
                workflowKey,
                addedBy
            )
            val workflow = flotaleWorkflowDomainSdk.findWorkflow.runOperation(workflowKey)
            val initialStage = flotaleStageDomainSdk.initialWorkflowStage.runOperation(workflow)
            val initialTask = flotaleTaskDomainSdk.initialStageTask.runOperation(initialStage)

            flotaleElementDomainSdk.createElement
                .runOperation(
                    CreateElementDto(
                        elementReference = ElementReferenceData(
                            elementKey = elementKey,
                            elementType = elementType,
                        ),
                        task = initialTask,
                        addedBy = addedBy
                    )
                )

            elementTaskBroadcaster.elementEnter(
                elementKey = elementKey,
                elementType = elementType,
                taskKey = initialTask.taskKey,
                executedBy = addedBy
            )
            logger.debug(
                "[Element: {}, type: {}] under [workflow: {}] has been initiated successfully by {}",
                elementKey,
                elementType,
                workflowKey,
                addedBy
            )
            elementDetails(
                elementKey = elementKey,
                elementType = elementType,
                requestedBy = addedBy
            )
        }
    }

    override fun validateExecuteAction(
        actionKey: String,
        elementKey: String,
        elementType: String,
        requestedBy: FlotaleUserTokenData,
    ) {
        logger.debug(
            "Validating [action: {}] for [element: {}, type: {}] by {}",
            actionKey,
            elementKey,
            elementType,
            requestedBy
        )

        val element = flotaleElementDomainSdk.findElementByReference
            .runOperation(
                ElementReferenceData(
                    elementKey = elementKey,
                    elementType = elementType
                )
            )
        val action = flotaleActionDomainSdk.taskActionByKey
            .runOperation(
                TaskActionByKeyDto(
                    task = element.task,
                    actionKey = actionKey
                )
            )

        actionCanBeExecuted(action = action, element = element, executedBy = requestedBy)
            .takeUnless { it }?.let {
                throw EngineErrors.ACTION_CANT_BE_EXECUTED.unprocessableEntity()
            }
        logger.debug(
            "Validating [action: {}] for [element: {}, type: {}] by {} has been successfully verified, Action can be executed without any expected issue",
            actionKey,
            elementKey,
            elementType,
            requestedBy
        )
    }

    override fun executeAction(
        actionKey: String,
        elementKey: String,
        elementType: String,
        executedBy: FlotaleUserTokenData,
        formData: Map<String, Any>,
    ) {
        flotaleTransactionalExecutor.runOnTransaction {
            logger.debug(
                "Executing [action: {}] for [element: {}, type: {}] by {}",
                actionKey,
                elementKey,
                elementType,
                executedBy
            )

            runCatching {
                val element = flotaleElementDomainSdk.findElementByReference
                    .runOperation(
                        ElementReferenceData(
                            elementKey = elementKey,
                            elementType = elementType
                        )
                    )

                val action = flotaleActionDomainSdk.taskActionByKey
                    .runOperation(
                        TaskActionByKeyDto(
                            task = element.task,
                            actionKey = actionKey
                        )
                    )

                actionCanBeExecuted(action = action, element = element, executedBy = executedBy)
                    .takeUnless { it }?.let {
                        logger.error(
                            "Executing [action: {}] for [element: {}, type: {}] by {} is prevented",
                            actionKey,
                            element.elementKey,
                            element.elementType,
                            executedBy
                        )
                        throw EngineErrors.ACTION_CANT_BE_EXECUTED.unprocessableEntity()
                    }

                if (action.actionType == ActionType.FORM_ACTION) {
                    executeFormAction(actionKey, element, executedBy, formData)
                } else {
                    executeStandardAction(actionKey, element, executedBy)
                }

                flotaleElementDomainSdk.elementExecuteAction
                    .runOperation(
                        ElementActionDto(
                            action = action,
                            element = element,
                            executedBy = executedBy
                        )
                    )

                elementTaskBroadcaster.runCatching {
                    elementExit(
                        elementKey = elementKey,
                        elementType = element.elementType,
                        taskKey = action.sourceTask.taskKey,
                        executedBy = executedBy
                    )
                    elementEnter(
                        elementKey = elementKey,
                        elementType = element.elementType,
                        taskKey = action.destinationTask.taskKey,
                        executedBy = executedBy
                    )
                }.onFailure {
                    logger.warn(
                        "An issue acquired while executing `TaskBroadcaster` for [element: {}, type: {}] and [action: {}], [reason: {}] (Issue will be ignored)",
                        elementKey,
                        elementType,
                        actionKey,
                        it.message
                    )
                }
            }.onFailure {
                logger.error(
                    "[Action: {}] for [element: {}, type: {}] by {} has been failed while execution, [error: {}]",
                    actionKey,
                    elementKey,
                    elementType,
                    executedBy,
                    it.message,
                    it
                )
                throw it
            }.onSuccess {
                logger.debug(
                    "[Action: {}] for [element: {}, type: {}] by {} has been executed successfully",
                    actionKey,
                    elementKey,
                    elementType,
                    executedBy
                )
            }
        }
    }

    private fun executeStandardAction(
        actionKey: String,
        element: ElementDomain,
        executedBy: FlotaleUserTokenData,
    ) {
        actionExecutor.executeAction(
            ExecuteActionData.standard(
                actionKey = actionKey,
                elementKey = element.elementKey,
                elementType = element.elementType,
                actor = executedBy
            )
        )
    }

    private fun executeFormAction(
        actionKey: String,
        element: ElementDomain,
        executedBy: FlotaleUserTokenData,
        formData: Map<String, Any>,
    ) {
        val formValidationResult = actionFormProvider.validateForm(
            ExecuteActionData.form(
                actionKey = actionKey,
                elementKey = element.elementKey,
                elementType = element.elementType,
                actor = executedBy,
                formData = formData,
            )
        )
        when (formValidationResult) {
            is FormValidationResult.Companion.Valid -> {
                logger.trace(
                    "Form data for [action: {}] and [element: {}, type: {}] has been validated successfully, proceeding to execute action",
                    actionKey,
                    element.elementKey,
                    element.elementType
                )
                actionExecutor.executeAction(
                    ExecuteActionData.form(
                        actionKey = actionKey,
                        elementKey = element.elementKey,
                        elementType = element.elementType,
                        actor = executedBy,
                        formData = formData,
                    )
                )
            }

            is FormValidationResult.Companion.Invalid -> {
                logger.error(
                    "Form data for [action: {}] and [element: {}, type: {}] is invalid, errors: {}, preventing action execution",
                    actionKey,
                    element.elementKey,
                    element.elementType,
                    formValidationResult.errors
                )
                throw formValidationResult.errors.badRequest()
            }
        }
    }

    override fun elementExist(
        elementKey: String,
        elementType: String,
    ): Boolean = flotaleElementDomainSdk.elementExist
        .runOperation(ElementReferenceData(elementKey = elementKey, elementType = elementType))

    override fun elementDetails(
        elementKey: String,
        elementType: String,
        requestedBy: FlotaleUserTokenData,
    ): ElementDetails {
        val element = flotaleElementDomainSdk.findElementByReference
            .runOperation(
                ElementReferenceData(
                    elementKey = elementKey,
                    elementType = elementType
                )
            )
        val actionDomains = flotaleActionDomainSdk.taskActions
            .runOperation(element.task)
        return ElementDetails(
            elementKey = elementKey,
            elementType = element.elementType,
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
            actions = actionDomains.filter { actionDomain ->
                actionCanBeExecuted(action = actionDomain, element = element, executedBy = requestedBy)
            }.map { actionDomain ->
                ActionDetails(
                    key = actionDomain.actionKey,
                    name = actionDomain.actionName,
                    actionMessage = actionDomain.actionMessage,
                    actionColor = actionDomain.actionColor,
                    actionHint = actionDomain.actionHint,
                    actionOutlined = actionDomain.actionOutlined,
                    successExecutionMessage = actionDomain.successExecutionMessage,
                    failedExecutionMessage = actionDomain.failedExecutionMessage,
                    formAction = actionDomain.actionType == ActionType.FORM_ACTION,
                    form = if (actionDomain.actionType == ActionType.FORM_ACTION) {
                        actionFormProvider.provideForm(
                            ExecuteActionData.standard(
                                actionKey = actionDomain.actionKey,
                                elementKey = elementKey,
                                elementType = element.elementType,
                                actor = requestedBy,
                            )
                        )
                    } else null
                )
            }
        )
    }

    private fun actionCanBeExecuted(action: ActionDomain, element: ElementDomain, executedBy: FlotaleUserTokenData) =
        actionExecutionValidator.validateExecution(
            ExecuteActionData.standard(
                actionKey = action.actionKey,
                elementKey = element.elementKey,
                elementType = element.elementType,
                actor = executedBy,
            )
        )
}