package io.arkitik.flotale.engine.core.ext

import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:48 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal typealias Command = () -> Unit

@DslMarker
annotation class Workflow

@DslMarker
annotation class Stage

@DslMarker
annotation class Task

@DslMarker
annotation class Action


class WorkflowDataBuilder {
    private lateinit var name: String
    private lateinit var key: String
    private var initialStage: StageData? = null
    private val stages = mutableListOf<StageData>()

    @Workflow
    infix fun name(name: String): WorkflowDataBuilder {
        this.name = name
        return this
    }

    @Workflow
    infix fun key(key: String): WorkflowDataBuilder {
        this.key = key
        return this
    }

    @Stage
    infix fun initialStage(builder: StageDataBuilder.() -> Unit): WorkflowDataBuilder {
        this.initialStage = StageDataBuilder().apply(builder).build()
        return this
    }

    @Stage
    infix fun stage(builder: StageDataBuilder.() -> Unit): WorkflowDataBuilder {
        this.stages.add(StageDataBuilder().apply(builder).build())
        return this
    }

    fun build(): WorkflowData {
        return WorkflowData(
            name = name,
            key = key,
            initialStage = initialStage,
            stages = stages
        )
    }
}

class StageDataBuilder {
    private lateinit var key: String
    private lateinit var name: String
    private var initialTask: TaskData? = null
    private var tasks: MutableList<TaskData> = ArrayList()

    @Stage
    infix fun stageKey(stageKey: String): StageDataBuilder {
        key = stageKey
        return this
    }

    @Stage
    infix fun stageName(stageName: String): StageDataBuilder {
        name = stageName
        return this
    }

    @Task
    infix fun stageInitialTask(builder: TaskDataBuilder.() -> Unit): StageDataBuilder {
        initialTask = TaskDataBuilder().apply(builder).build()
        return this
    }

    @Task
    infix fun stageTask(builder: TaskDataBuilder.() -> Unit): StageDataBuilder {
        tasks.add(TaskDataBuilder().apply(builder).build())
        return this
    }

    fun build() =
        StageData(
            key = key,
            name = name,
            initialTask = initialTask,
            tasks = tasks,
        )
}

class TaskDataBuilder {
    private lateinit var key: String
    private lateinit var name: String
    private var terminal: Boolean = false
    private val actions = mutableListOf<ActionData>()

    @Task
    fun taskKey(key: String): TaskDataBuilder {
        this.key = key
        return this
    }

    @Task
    fun taskName(name: String): TaskDataBuilder {
        this.name = name
        return this
    }

    @Task
    fun terminal(terminal: Boolean): TaskDataBuilder {
        this.terminal = terminal
        return this
    }

    @Action
    fun taskAction(builder: ActionDataBuilder.() -> Unit): TaskDataBuilder {
        this.actions.add(ActionDataBuilder().apply(builder).build())
        return this
    }

    fun build() =
        TaskData(
            key = key,
            name = name,
            terminal = terminal,
            actions = actions
        )
}

class ActionDataBuilder {
    private lateinit var key: String
    private lateinit var name: String
    private lateinit var destinationTask: String

    @Action
    fun actionKey(key: String): ActionDataBuilder {
        this.key = key
        return this
    }

    @Action
    fun actionName(name: String): ActionDataBuilder {
        this.name = name
        return this
    }

    @Action
    fun actionDestinationTask(destinationTask: String): ActionDataBuilder {
        this.destinationTask = destinationTask
        return this
    }

    fun build() =
        ActionData(
            key = key,
            name = name,
            destinationTaskKey = destinationTask,
        )
}
