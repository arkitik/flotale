package io.arkitik.flotale.engine.operation.domain

import io.arkitik.flotale.action.domain.embedded.ActionType
import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.action.sdk.dto.CreateActionDto
import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData
import io.arkitik.flotale.engine.core.dto.WorkflowValidationResult
import io.arkitik.flotale.engine.core.dto.WorkflowValidationResult.Companion.InvalidReason
import io.arkitik.flotale.protocol.transactional.FlotaleTransactionalExecutor
import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.sdk.FlotaleStageDomainSdk
import io.arkitik.flotale.stage.sdk.dto.CreateStageDto
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.sdk.FlotaleTaskDomainSdk
import io.arkitik.flotale.task.sdk.dto.CreateTaskDto
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.sdk.FlotaleWorkflowDomainSdk
import io.arkitik.flotale.workflow.sdk.dto.CreateWorkflowDto
import io.arkitik.radix.develop.operation.ext.runOperation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:58 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleDomainEngineImpl(
    private val flotaleWorkflowDomainSdk: FlotaleWorkflowDomainSdk,
    private val flotaleStageDomainSdk: FlotaleStageDomainSdk,
    private val flotaleTaskDomainSdk: FlotaleTaskDomainSdk,
    private val flotaleActionDomainSdk: FlotaleActionDomainSdk,
    private val flotaleTransactionalExecutor: FlotaleTransactionalExecutor,
) : FlotaleDomainEngine {
    override fun registerWorkflows(workflows: List<WorkflowData>) {
        workflows.forEach(::registerWorkflow)
    }

    override fun registerWorkflow(workflow: WorkflowData) {
        flotaleTransactionalExecutor.runOnTransaction {
            createWorkflow(workflow)

            workflow.initialStage?.let { initialStage ->
                registerStage(
                    workflowKey = workflow.key,
                    stage = initialStage,
                    initialStage = true
                )
            }

            workflow.stages.forEach { stage ->
                registerStage(
                    workflowKey = workflow.key,
                    stage = stage,
                    initialStage = false
                )
            }
        }
    }

    /**
     * Validates a workflow for proper configuration and executable state.
     * Performs multiple validation checks:
     * 1. Workflow exists
     * 2. Workflow has stages
     * 3. Workflow has an initial stage
     * 4. Each stage has at least one task
     * 5. All tasks are reachable
     * 6. All destination tasks exist
     * 7. Tasks either have outgoing actions or are marked as terminal
     *
     * @param workflowKey Unique identifier of the workflow to validate
     * @return WorkflowValidationResult indicating validity or containing error details
     */
    override fun validateWorkflow(workflowKey: String): WorkflowValidationResult {
        val errors = mutableListOf<InvalidReason>()

        val workflowDomain = runCatching {
            findWorkflow(workflowKey)
        }.getOrNull() ?: return WorkflowValidationResult.invalid(workflowKey, "Workflow with key does not exist")

        // Step 2: Validate workflow has at least one stage
        val stages = flotaleStageDomainSdk.workflowStages.runOperation(workflowDomain)
        if (stages.isEmpty()) {
            return WorkflowValidationResult.invalid(InvalidReason(workflowKey, "Workflow does not have any stages"))
        }
        flotaleStageDomainSdk.runCatching {
            initialWorkflowStage.runOperation(workflowDomain)
        }.onFailure {
            errors.add(
                InvalidReason(
                    workflowKey,
                    "Workflow does not have an initial stage"
                )
            )
        }

        val stagesWithoutTasks = mutableListOf<String>()
        val stagesWithoutInitialTask = mutableListOf<String>()
        val unreachableTasks = mutableSetOf<String>()
        val destinationTaskKeys = mutableSetOf<String>()

        // Get the initial stage for special validation
        val initialStage = flotaleStageDomainSdk.runCatching {
            initialWorkflowStage.runOperation(workflowDomain)
        }.getOrNull()

        stages.forEach { stage ->
            val tasks = flotaleTaskDomainSdk.stageTasks
                .runOperation(stage)

            if (tasks.isEmpty()) {
                stagesWithoutTasks.add(stage.stageKey)
            } else {
                // Only check for initial task if this is the initial stage
                val isInitialStage = initialStage?.uuid == stage.uuid

                val taskDomain = flotaleTaskDomainSdk.runCatching {
                    initialStageTask.runOperation(stage)
                }.onFailure {
                    // Only add to errors if this is the initial stage
                    if (isInitialStage) {
                        stagesWithoutInitialTask.add(stage.stageKey)
                    }
                }.getOrNull()

                // Track all tasks for reachability check
                tasks.forEach { task ->
                    if (taskDomain?.uuid != task.uuid) {
                        unreachableTasks.add(task.taskKey)
                    }

                    // Check for actions and collect destination tasks
                    val actions = flotaleActionDomainSdk.taskActions.runOperation(task)
                    actions.forEach { action ->
                        destinationTaskKeys.add(action.destinationTask.taskKey)
                    }
                }
            }
        }

        // Add errors for stages without tasks
        if (stagesWithoutTasks.isNotEmpty()) {
            errors.add(
                InvalidReason(
                    workflowKey,
                    "The following stages do not have any tasks: ${stagesWithoutTasks.joinToString()}"
                )
            )
        }

        // Add errors for initial stage without initial task
        if (stagesWithoutInitialTask.isNotEmpty()) {
            errors.add(
                InvalidReason(
                    workflowKey,
                    "The initial stage does not have an initial task: ${stagesWithoutInitialTask.joinToString()}"
                )
            )
        }

        // Step 5: Check for reachability of tasks
        // Remove tasks that are reachable via actions
        unreachableTasks.removeAll(destinationTaskKeys)

        // Any remaining tasks in the set are unreachable
        if (unreachableTasks.isNotEmpty()) {
            errors.add(
                InvalidReason(
                    workflowKey,
                    "The following tasks are unreachable: ${unreachableTasks.joinToString()}"
                )
            )
        }

        // Step 6: Check for non-existent destination tasks
        val allTaskKeys = mutableSetOf<String>()
        stages.forEach { stage ->
            val tasks = flotaleTaskDomainSdk.stageTasks.runOperation(stage)
            tasks.forEach { task ->
                allTaskKeys.add(task.taskKey)
            }
        }

        val invalidDestinations = destinationTaskKeys.filter { !allTaskKeys.contains(it) }
        if (invalidDestinations.isNotEmpty()) {
            errors.add(
                InvalidReason(
                    workflowKey,
                    "The following destination tasks do not exist: ${invalidDestinations.joinToString()}"
                )
            )
        }

        // Step 7: Check for terminal tasks (tasks without outgoing actions)
        val tasksWithoutOutgoingActions = mutableListOf<String>()
        stages.forEach { stage ->
            val tasks = flotaleTaskDomainSdk.stageTasks.runOperation(stage)
            tasks.forEach { task ->
                val actions = flotaleActionDomainSdk.taskActions.runOperation(task)
                if (actions.isEmpty() && !task.terminalTask) {
                    tasksWithoutOutgoingActions.add(task.taskKey)
                }
            }
        }

        if (tasksWithoutOutgoingActions.isNotEmpty()) {
            errors.add(
                InvalidReason(
                    workflowKey,
                    "The following tasks have no outgoing actions and are not marked as terminal tasks: ${tasksWithoutOutgoingActions.joinToString()}"
                )
            )
        }

        // Return the validation result
        return if (errors.isEmpty()) {
            WorkflowValidationResult.valid()
        } else {
            WorkflowValidationResult.invalid(errors)
        }
    }

    private fun createWorkflow(workflow: WorkflowData) {
        val createWorkflowDto = CreateWorkflowDto(
            workflowKey = workflow.key,
            workflowName = workflow.name
        )
        flotaleWorkflowDomainSdk.createWorkflow.runOperation(createWorkflowDto)
    }

    override fun registerStage(workflowKey: String, stage: StageData, initialStage: Boolean) {
        flotaleTransactionalExecutor.runOnTransaction {
            val workflowDomain = findWorkflow(workflowKey)
            createStage(workflowDomain, stage, initialStage)

            stage.initialTask?.let { initialTask ->
                registerTask(
                    stageKey = stage.key,
                    task = initialTask,
                    initialTask = true
                )
            }

            stage.tasks.forEach { task ->
                registerTask(
                    stageKey = stage.key,
                    task = task,
                    initialTask = false
                )
            }
        }
    }

    private fun findWorkflow(workflowKey: String): WorkflowDomain {
        return flotaleTransactionalExecutor.runOnTransaction {
            flotaleWorkflowDomainSdk.findWorkflow.runOperation(workflowKey)
        }
    }

    private fun createStage(workflowDomain: WorkflowDomain, stage: StageData, initialStage: Boolean) {
        flotaleTransactionalExecutor.runOnTransaction {
            val createStageDto = CreateStageDto(
                workflow = workflowDomain,
                stageKey = stage.key,
                stageName = stage.name,
                initialStage = initialStage
            )
            flotaleStageDomainSdk.createStage.runOperation(createStageDto)
        }
    }

    override fun registerTask(stageKey: String, task: TaskData, initialTask: Boolean) {
        flotaleTransactionalExecutor.runOnTransaction {
            val stageDomain = findStage(stageKey)
            createTask(stageDomain, task, initialTask)

            task.actions.forEach { action ->
                addAction(task.key, action)
            }
        }
    }

    private fun findStage(stageKey: String): StageDomain {
        return flotaleStageDomainSdk.findStage.runOperation(stageKey)
    }

    private fun createTask(stageDomain: StageDomain, task: TaskData, initialTask: Boolean) {
        flotaleTransactionalExecutor.runOnTransaction {
            val createTaskDto = CreateTaskDto(
                stage = stageDomain,
                taskKey = task.key,
                taskName = task.name,
                terminal = task.terminal,
                initialTask = initialTask
            )
            flotaleTaskDomainSdk.createTask.runOperation(createTaskDto)
        }
    }

    override fun addAction(taskKey: String, action: ActionData) {
        flotaleTransactionalExecutor.runOnTransaction {
            val sourceTask = findTask(taskKey)
            val destinationTask = findTask(action.destinationTaskKey)

            val createActionDto = CreateActionDto(
                actionKey = action.key,
                actionName = action.name,
                actionType = if (action.formAction) ActionType.FORM_ACTION else ActionType.STANDARD,
                sourceTask = sourceTask,
                destinationTask = destinationTask
            )
            flotaleActionDomainSdk.createAction.runOperation(createActionDto)
        }
    }

    private fun findTask(taskKey: String): TaskDomain {
        return flotaleTaskDomainSdk.findTask.runOperation(taskKey)
    }

    override fun deleteWorkflow(workflowKey: String) {
        flotaleTransactionalExecutor.runOnTransaction {
            val workflowDomain = findWorkflow(workflowKey)
            flotaleWorkflowDomainSdk.deleteWorkflow.runOperation(workflowDomain)

            val stages = flotaleStageDomainSdk.workflowStages.runOperation(workflowDomain)
            stages.forEach(::deleteStage)
        }
    }

    override fun deleteStage(stageKey: String) {
        flotaleTransactionalExecutor.runOnTransaction {
            val stageDomain = findStage(stageKey)
            deleteStage(stageDomain)
        }
    }

    private fun deleteStage(stageDomain: StageDomain) {
        flotaleTransactionalExecutor.runOnTransaction {
            flotaleStageDomainSdk.deleteStage.runOperation(stageDomain)

            val tasks = flotaleTaskDomainSdk.stageTasks.runOperation(stageDomain)
            tasks.forEach(::deleteTask)
        }
    }

    override fun deleteTask(taskKey: String) {
        flotaleTransactionalExecutor.runOnTransaction {
            val taskDomain = findTask(taskKey)
            deleteTask(taskDomain)
        }
    }

    private fun deleteTask(taskDomain: TaskDomain) {
        flotaleTransactionalExecutor.runOnTransaction {
            flotaleTaskDomainSdk.deleteTask.runOperation(taskDomain)

            val actions = flotaleActionDomainSdk.taskActions.runOperation(taskDomain)
            actions.forEach { actionDomain ->
                flotaleActionDomainSdk.deleteAction.runOperation(actionDomain)
            }
        }
    }

    override fun deleteAction(actionKey: String) {
        flotaleTransactionalExecutor.runOnTransaction {
            val actionDomain = flotaleActionDomainSdk.findAction.runOperation(actionKey)
            flotaleActionDomainSdk.deleteAction.runOperation(actionDomain)
        }
    }
}
