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
        addStandardAction(builder)
    }

    fun addStandardAction(builder: ActionDataBuilder.() -> Unit) {
        addAction(ActionDataBuilder(false).apply(builder).build())
    }

    fun addFormAction(builder: ActionDataBuilder.() -> Unit) {
        addAction(ActionDataBuilder(true).apply(builder).build())
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
class ActionDataBuilder(private val formAction: Boolean = false) {
    lateinit var actionKey: String
    lateinit var actionName: String
    lateinit var actionDestinationTask: String

    var actionMessage: String? = null
    var actionColor: ActionColor = ActionColor.primary()
    var actionHint: String? = null
    var actionOutlined: Boolean = false
    var successExecutionMessage: String? = null
    var failedExecutionMessage: String? = null

    fun build() =
        ActionData(
            key = actionKey,
            name = actionName,
            destinationTaskKey = actionDestinationTask,
            formAction = formAction,
            actionMessage = actionMessage,
            actionColor = actionColor.name,
            actionHint = actionHint,
            actionOutlined = actionOutlined,
            successExecutionMessage = successExecutionMessage,
            failedExecutionMessage = failedExecutionMessage,
        )
}

sealed interface ActionColor {
    val name: String
        get() = javaClass.simpleName.lowercase()

    companion object {
        data object Primary : ActionColor
        data object Accent : ActionColor
        data object Warn : ActionColor
        class Custom(override val name: String) : ActionColor

        fun primary() = Primary
        fun accent() = Accent
        fun warn() = Warn
        fun custom(name: String) = Custom(name)
    }
}