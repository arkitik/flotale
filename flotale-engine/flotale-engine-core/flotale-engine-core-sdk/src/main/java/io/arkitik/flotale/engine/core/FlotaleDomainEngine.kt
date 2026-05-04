package io.arkitik.flotale.engine.core

import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData
import io.arkitik.flotale.engine.core.dto.WorkflowValidationResult


/**
 * Domain engine for managing workflow components (workflows, stages, tasks, and actions).
 * Provides operations for registering, managing, and deleting workflow elements.
 *
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:34 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleDomainEngine {
    // Workflow operations
    /**
     * Registers multiple workflow definitions
     * @param workflows List of workflow data to register
     */
    fun registerWorkflows(workflows: List<WorkflowData>)

    /**
     * Registers a single workflow definition
     * @param workflow Workflow data to register
     */
    fun registerWorkflow(workflow: WorkflowData)

    /**
     * Validates if a workflow is properly configured and can be executed
     * @param workflowKey Unique identifier of the workflow to validate
     * @return Result containing validation status and any error messages
     */
    fun validateWorkflow(workflowKey: String): WorkflowValidationResult

    /**
     * Deletes a workflow and all its associated stages, tasks, and actions
     * @param workflowKey Unique identifier of the workflow to delete
     */
    fun deleteWorkflow(workflowKey: String)

    // Stage operations
    /**
     * Registers a stage within a workflow (non-initial by default)
     * @param workflowKey Unique identifier of the parent workflow
     * @param stage Stage data to register
     * @return Unit
     */
    fun registerStage(workflowKey: String, stage: StageData): Unit =
        registerStage(workflowKey, stage, initialStage = false)

    /**
     * Registers a stage within a workflow with a specified initial status
     * @param workflowKey Unique identifier of the parent workflow
     * @param stage Stage data to register
     * @param initialStage Whether this stage is the initial stage in the workflow
     */
    fun registerStage(workflowKey: String, stage: StageData, initialStage: Boolean)

    /**
     * Deletes a stage and all its associated tasks and actions
     * @param stageKey Unique identifier of the stage to delete
     */
    fun deleteStage(stageKey: String)

    // Task operations
    /**
     * Registers a task within a stage (non-initial by default)
     * @param stageKey Unique identifier of the parent stage
     * @param task Task data to register
     * @return Unit
     */
    fun registerTask(stageKey: String, task: TaskData): Unit =
        registerTask(stageKey, task, initialTask = false)

    /**
     * Registers a task within a stage with specified initial status
     * @param stageKey Unique identifier of the parent stage
     * @param task Task data to register
     * @param initialTask Whether this task is the initial task in the stage
     */
    fun registerTask(stageKey: String, task: TaskData, initialTask: Boolean)

    /**
     * Deletes a task and all its associated actions
     * @param taskKey Unique identifier of the task to delete
     */
    fun deleteTask(taskKey: String)

    // Action operations
    /**
     * Adds an action to a task
     * @param taskKey Unique identifier of the parent task
     * @param action Action data to add
     */
    fun addAction(taskKey: String, action: ActionData)

    /**
     * Deletes an action
     * @param actionKey Unique identifier of the action to delete
     */
    fun deleteAction(actionKey: String)
}
