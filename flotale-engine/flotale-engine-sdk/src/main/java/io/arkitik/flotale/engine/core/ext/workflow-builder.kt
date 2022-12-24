package io.arkitik.flotale.engine.core.ext

import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:48 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */

fun FlotaleDomainEngine.persistWorkflow(
    builder: FlotaleWorkflowsBuilder.() -> Unit,
) {
    val commands = buildList<() -> Unit> {
        val workflows = FlotaleWorkflowsBuilder().apply(builder).build()
        workflows.forEach { workflow ->
            add {
                registerWorkflow(
                    WorkflowData(
                        key = workflow.key,
                        name = workflow.name,
                    )
                )
            }

            workflow.initialStage?.let { stage ->
                add {
                    registerStage(workflowKey = workflow.key, stage = StageData(stage.key, stage.name), true)
                }

                stage.initialTask?.let { task ->
                    add {
                        registerTask(stage.key, TaskData(task.key, task.name), true)
                    }
                }
                stage.tasks
                    .forEach { task ->
                        add {
                            registerTask(stage.key, TaskData(task.key, task.name))
                        }
                    }
            }

            workflow.stages
                .forEach { stage ->
                    add {
                        registerStage(workflowKey = workflow.key, stage = StageData(stage.key, stage.name))
                    }

                    stage.initialTask?.let { task ->
                        add {
                            registerTask(stage.key, TaskData(task.key, task.name), true)
                        }
                    }

                    stage.tasks
                        .forEach { task ->
                            add {
                                registerTask(stage.key, TaskData(task.key, task.name))
                            }
                        }
                }
        }

        workflows.forEach { workflow ->
            workflow.stages
                .forEach { stage ->
                    stage.tasks
                        .forEach { task ->
                            task.actions
                                .forEach { action ->
                                    add {
                                        addAction(taskKey = task.key, action)
                                    }
                                }
                        }
                    stage.initialTask?.let { task ->
                        task.actions
                            .forEach { action ->
                                add {
                                    addAction(taskKey = task.key, action)
                                }
                            }
                    }
                }

            workflow.initialStage?.let { stage ->
                stage.tasks.forEach { task ->
                    task.actions
                        .forEach { action ->
                            add {
                                addAction(taskKey = task.key, action)
                            }
                        }
                }
                stage.initialTask?.let { task ->
                    task.actions
                        .forEach { action ->
                            add {
                                addAction(taskKey = task.key, action)
                            }
                        }
                }
            }
        }
    }
    commands.forEach { it.invoke() }
}

fun workflow(
    builder: WorkflowDataBuilder.() -> Unit,
) = WorkflowDataBuilder()
    .apply(builder)

fun stage(
    builder: StageDataBuilder.() -> Unit,
) =
    StageDataBuilder()
        .apply(builder)
        .build()

fun task(
    builder: TaskDataBuilder.() -> Unit,
) =
    TaskDataBuilder()
        .apply(builder)
        .build()

fun action(
    builder: ActionDataBuilder.() -> Unit,
) =
    ActionDataBuilder()
        .apply(builder)
        .build()


class WorkflowDataBuilder {
    private lateinit var name: String
    private lateinit var key: String
    private var initialStage: StageData? = null
    private val stages = mutableListOf<StageData>()


    infix fun name(name: String): WorkflowDataBuilder {
        this.name = name
        return this
    }

    infix fun key(key: String): WorkflowDataBuilder {
        this.key = key
        return this
    }

    infix fun initialStage(builder: StageDataBuilder.() -> Unit): WorkflowDataBuilder {
        this.initialStage = StageDataBuilder().apply(builder).build()
        return this
    }

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

    infix fun stageKey(stageKey: String): StageDataBuilder {
        key = stageKey
        return this
    }

    infix fun stageName(stageName: String): StageDataBuilder {
        name = stageName
        return this
    }

    infix fun stageInitialTask(builder: TaskDataBuilder.() -> Unit): StageDataBuilder {
        initialTask = TaskDataBuilder().apply(builder).build()
        return this
    }

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
    private val actions = mutableListOf<ActionData>()

    fun taskKey(key: String): TaskDataBuilder {
        this.key = key
        return this
    }

    fun taskName(name: String): TaskDataBuilder {
        this.name = name
        return this
    }

    fun taskAction(builder: ActionDataBuilder.() -> Unit): TaskDataBuilder {
        this.actions.add(ActionDataBuilder().apply(builder).build())
        return this
    }

    fun build() =
        TaskData(
            key = key,
            name = name,
            actions = actions
        )
}

class ActionDataBuilder {
    private lateinit var key: String
    private lateinit var name: String
    private lateinit var destinationTask: String

    fun actionKey(key: String): ActionDataBuilder {
        this.key = key
        return this
    }

    fun actionName(name: String): ActionDataBuilder {
        this.name = name
        return this
    }

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
