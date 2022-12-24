package io.arkitik.flotale.engine.operation.domain

import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.action.sdk.dto.CreateActionDto
import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData
import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.sdk.FlotaleStageDomainSdk
import io.arkitik.flotale.stage.sdk.dto.CreateStageDto
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.sdk.FlotaleTaskDomainSdk
import io.arkitik.flotale.task.sdk.dto.CreateTaskDto
import io.arkitik.flotale.workflow.sdk.FlotaleWorkflowDomainSdk
import io.arkitik.flotale.workflow.sdk.dto.CreateWorkflowDto
import io.arkitik.radix.develop.operation.ext.runOperation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:58 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleDomainEngineImpl(
    private val flotaleWorkflowDomainSdk: FlotaleWorkflowDomainSdk,
    private val flotaleStageDomainSdk: FlotaleStageDomainSdk,
    private val flotaleTaskDomainSdk: FlotaleTaskDomainSdk,
    private val flotaleActionDomainSdk: FlotaleActionDomainSdk,
) : FlotaleDomainEngine {
    override fun registerWorkflow(workflow: WorkflowData) {
        flotaleWorkflowDomainSdk.createWorkflow
            .runOperation(
                CreateWorkflowDto(
                    workflowKey = workflow.key,
                    workflowName = workflow.name
                )
            )
        workflow.initialStage?.let {
            registerStage(
                workflowKey = workflow.key,
                stage = it,
                initialStage = true
            )
        }
        workflow.stages
            .forEach {
                registerStage(
                    workflowKey = workflow.key,
                    stage = it,
                    initialStage = true
                )
            }
    }

    override fun registerStage(workflowKey: String, stage: StageData, initialStage: Boolean) {
        val workflowDomain = flotaleWorkflowDomainSdk.findWorkflow.runOperation(workflowKey)
        flotaleStageDomainSdk.createStage
            .runOperation(
                CreateStageDto(
                    workflow = workflowDomain,
                    stageKey = stage.key,
                    stageName = stage.name,
                    initialStage = initialStage
                )
            )
        stage.initialTask?.let {
            registerTask(
                stageKey = stage.key,
                task = it,
                initialTask = true
            )
        }
        stage.tasks.forEach {
            registerTask(
                stageKey = stage.key,
                task = it,
                initialTask = false
            )
        }
    }

    override fun registerTask(stageKey: String, task: TaskData, initialTask: Boolean) {
        val stageDomain = flotaleStageDomainSdk.findStage.runOperation(stageKey)
        flotaleTaskDomainSdk.createTask
            .runOperation(
                CreateTaskDto(
                    stage = stageDomain,
                    taskKey = task.key,
                    taskName = task.name,
                    initialTask = initialTask
                )
            )
        task.actions
            .forEach {
                addAction(
                    taskKey = task.key,
                    action = it
                )
            }
    }

    override fun addAction(taskKey: String, action: ActionData) {
        val sourceTask = flotaleTaskDomainSdk.findTask.runOperation(taskKey)
        val destinationTask = flotaleTaskDomainSdk.findTask.runOperation(action.destinationTaskKey)
        flotaleActionDomainSdk.createAction
            .runOperation(
                CreateActionDto(
                    actionKey = action.key,
                    actionName = action.name,
                    sourceTask = sourceTask,
                    destinationTask = destinationTask
                )
            )
    }

    override fun deleteWorkflow(key: String) {
        val workflowDomain = flotaleWorkflowDomainSdk.findWorkflow
            .runOperation(key)
        flotaleWorkflowDomainSdk.deleteWorkflow
            .runOperation(workflowDomain)
        flotaleStageDomainSdk.workflowStages
            .runOperation(workflowDomain)
            .forEach {
                deleteStage(it)
            }
    }

    override fun deleteStage(key: String) {
        val stageDomain = flotaleStageDomainSdk.findStage
            .runOperation(key)
        deleteStage(stageDomain)
    }

    override fun deleteTask(key: String) {
        val taskDomain = flotaleTaskDomainSdk.findTask
            .runOperation(key)
        deleteTask(taskDomain)
    }

    override fun deleteAction(key: String) {
        val actionDomain = flotaleActionDomainSdk.findAction.runOperation(key)
        flotaleActionDomainSdk.deleteAction.runOperation(actionDomain)
    }

    private fun deleteStage(stageDomain: StageDomain) {
        flotaleStageDomainSdk.deleteStage
            .runOperation(stageDomain)
        flotaleTaskDomainSdk.stageTasks
            .runOperation(stageDomain)
            .forEach(::deleteTask)
    }

    private fun deleteTask(taskDomain: TaskDomain) {
        flotaleTaskDomainSdk.deleteTask.runOperation(taskDomain)
        flotaleActionDomainSdk.taskActions
            .runOperation(taskDomain)
            .forEach(flotaleActionDomainSdk.deleteAction::runOperation)
    }
}
