package io.arkitik.flotale.engine.core.ext

import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:48 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal typealias Command = () -> Unit

@DslMarker
annotation class FlotaleWorkflow

@FlotaleWorkflow
class WorkflowDataBuilder {
    lateinit var workflowName: String
    lateinit var workflowKey: String
    var initialStage: StageData? = null
    val stages = mutableListOf<StageData>()

    fun addStage(stageData: StageData) {
        this.stages.add(stageData)
    }

    fun addStage(builder: StageDataBuilder.() -> Unit) {
        addStage(StageDataBuilder().apply(builder).build())
    }

    fun initialStage(builder: StageDataBuilder.() -> Unit) {
        initialStage = StageDataBuilder().apply(builder).build()
    }

    fun build(): WorkflowData {
        return WorkflowData(
            name = workflowName,
            key = workflowKey,
            initialStage = initialStage,
            stages = stages
        )
    }
}

@FlotaleWorkflow
class StageDataBuilder {
    lateinit var stageKey: String
    lateinit var stageName: String
    var initialTask: TaskData? = null
    private var tasks: MutableList<TaskData> = ArrayList()

    fun addTask(taskData: TaskData) {
        tasks.add(taskData)
    }

    fun addTask(builder: TaskDataBuilder.() -> Unit) {
        addTask(TaskDataBuilder().apply(builder).build())
    }

    fun initialTask(builder: TaskDataBuilder.() -> Unit) {
        initialTask = TaskDataBuilder().apply(builder).build()
    }

    fun build() =
        StageData(
            key = stageKey,
            name = stageName,
            initialTask = initialTask,
            tasks = tasks,
        )
}

@FlotaleWorkflow
class TaskDataBuilder {
    lateinit var taskKey: String
    lateinit var taskName: String
    var terminal: Boolean = false
    private val taskActions = mutableListOf<ActionData>()

    fun addAction(actionData: ActionData) {
        this.taskActions.add(actionData)
    }

    fun addAction(builder: ActionDataBuilder.() -> Unit) {
        addAction(ActionDataBuilder().apply(builder).build())
    }

    fun build() =
        TaskData(
            key = taskKey,
            name = taskName,
            terminal = terminal,
            actions = taskActions
        )
}

@FlotaleWorkflow
class ActionDataBuilder {
    lateinit var actionKey: String
    lateinit var actionName: String
    lateinit var actionDestinationTask: String

    fun build() =
        ActionData(
            key = actionKey,
            name = actionName,
            destinationTaskKey = actionDestinationTask,
        )
}