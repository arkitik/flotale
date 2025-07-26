package io.arkitik.flotale.engine.operation.domain

import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.action.sdk.dto.CreateActionDto
import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData
import io.arkitik.flotale.engine.core.dto.WorkflowValidationResult
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
) : FlotaleDomainEngine {
    override fun registerWorkflows(workflows: List<WorkflowData>) {
        workflows.forEach(::registerWorkflow)
    }

    override fun registerWorkflow(workflow: WorkflowData) {
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

    override fun validateWorkflow(workflowKey: String): WorkflowValidationResult {
        val errors = mutableListOf<String>()

        val workflowDomain = runCatching {
            findWorkflow(workflowKey)
        }.getOrNull() ?: return WorkflowValidationResult.invalid("Workflow with key '$workflowKey' does not exist")

        // Step 2: Validate workflow has at least one stage
        val stages = flotaleStageDomainSdk.workflowStages.runOperation(workflowDomain)
        if (stages.isEmpty()) {
            errors.add("Workflow '$workflowKey' does not have any stages")
            return WorkflowValidationResult.invalid(errors)
        }
        flotaleStageDomainSdk.runCatching {
            initialWorkflowStage.runOperation(workflowDomain)
        }.onFailure {
            errors.add("Workflow '$workflowKey' does not have an initial stage")
        }

        // Step 4: Validate each stage has at least one task
        val stagesWithoutTasks = mutableListOf<String>()
        val stagesWithoutInitialTask = mutableListOf<String>()
        val unreachableTasks = mutableSetOf<String>()
        val destinationTaskKeys = mutableSetOf<String>()

        stages.forEach { stage ->
            val tasks = flotaleTaskDomainSdk.stageTasks
                .runOperation(stage)

            if (tasks.isEmpty()) {
                stagesWithoutTasks.add(stage.stageKey)
            } else {
                val taskDomain = flotaleTaskDomainSdk.runCatching {
                    initialStageTask.runOperation(stage)
                }.onFailure {
                    stagesWithoutInitialTask.add(stage.stageKey)
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
            errors.add("The following stages do not have any tasks: ${stagesWithoutTasks.joinToString()}")
        }

        // Add errors for stages without initial tasks
        if (stagesWithoutInitialTask.isNotEmpty()) {
            errors.add("The following stages do not have an initial task: ${stagesWithoutInitialTask.joinToString()}")
        }

        // Step 5: Check for reachability of tasks
        // Remove tasks that are reachable via actions
        unreachableTasks.removeAll(destinationTaskKeys)

        // Any remaining tasks in the set are unreachable
        if (unreachableTasks.isNotEmpty()) {
            errors.add("The following tasks are unreachable: ${unreachableTasks.joinToString()}")
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
            errors.add("The following destination tasks do not exist: ${invalidDestinations.joinToString()}")
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
            errors.add("The following tasks have no outgoing actions and are not marked as terminal tasks: ${tasksWithoutOutgoingActions.joinToString()}")
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

    private fun findWorkflow(workflowKey: String): WorkflowDomain {
        return flotaleWorkflowDomainSdk.findWorkflow.runOperation(workflowKey)
    }

    private fun createStage(workflowDomain: WorkflowDomain, stage: StageData, initialStage: Boolean) {
        val createStageDto = CreateStageDto(
            workflow = workflowDomain,
            stageKey = stage.key,
            stageName = stage.name,
            initialStage = initialStage
        )
        flotaleStageDomainSdk.createStage.runOperation(createStageDto)
    }

    override fun registerTask(stageKey: String, task: TaskData, initialTask: Boolean) {
        val stageDomain = findStage(stageKey)
        createTask(stageDomain, task, initialTask)

        task.actions.forEach { action ->
            addAction(task.key, action)
        }
    }

    private fun findStage(stageKey: String): StageDomain {
        return flotaleStageDomainSdk.findStage.runOperation(stageKey)
    }

    private fun createTask(stageDomain: StageDomain, task: TaskData, initialTask: Boolean) {
        val createTaskDto = CreateTaskDto(
            stage = stageDomain,
            taskKey = task.key,
            taskName = task.name,
            terminal = task.terminal,
            initialTask = initialTask
        )
        flotaleTaskDomainSdk.createTask.runOperation(createTaskDto)
    }

    override fun addAction(taskKey: String, action: ActionData) {
        val sourceTask = findTask(taskKey)
        val destinationTask = findTask(action.destinationTaskKey)

        val createActionDto = CreateActionDto(
            actionKey = action.key,
            actionName = action.name,
            sourceTask = sourceTask,
            destinationTask = destinationTask
        )
        flotaleActionDomainSdk.createAction.runOperation(createActionDto)
    }

    private fun findTask(taskKey: String): TaskDomain {
        return flotaleTaskDomainSdk.findTask.runOperation(taskKey)
    }

    override fun deleteWorkflow(workflowKey: String) {
        val workflowDomain = findWorkflow(workflowKey)
        flotaleWorkflowDomainSdk.deleteWorkflow.runOperation(workflowDomain)

        val stages = flotaleStageDomainSdk.workflowStages.runOperation(workflowDomain)
        stages.forEach(::deleteStage)
    }

    override fun deleteStage(stageKey: String) {
        val stageDomain = findStage(stageKey)
        deleteStage(stageDomain)
    }

    private fun deleteStage(stageDomain: StageDomain) {
        flotaleStageDomainSdk.deleteStage.runOperation(stageDomain)

        val tasks = flotaleTaskDomainSdk.stageTasks.runOperation(stageDomain)
        tasks.forEach(::deleteTask)
    }

    override fun deleteTask(taskKey: String) {
        val taskDomain = findTask(taskKey)
        deleteTask(taskDomain)
    }

    private fun deleteTask(taskDomain: TaskDomain) {
        flotaleTaskDomainSdk.deleteTask.runOperation(taskDomain)

        val actions = flotaleActionDomainSdk.taskActions.runOperation(taskDomain)
        actions.forEach { actionDomain ->
            flotaleActionDomainSdk.deleteAction.runOperation(actionDomain)
        }
    }

    override fun deleteAction(actionKey: String) {
        val actionDomain = flotaleActionDomainSdk.findAction.runOperation(actionKey)
        flotaleActionDomainSdk.deleteAction.runOperation(actionDomain)
    }
}
