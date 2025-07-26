package io.arkitik.flotale.engine.core.ext

import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 9:20 PM, 22 , **Thu, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@FlotaleWorkflow
class FlotaleWorkflowsBuilder {
    private val workflows = mutableListOf<WorkflowData>()
    fun addWorkflow(workflowDataBuilder: WorkflowDataBuilder.() -> Unit): FlotaleWorkflowsBuilder {
        workflows.add(WorkflowDataBuilder().apply(workflowDataBuilder).build())
        return this
    }

    fun build(): List<WorkflowData> = workflows
}

@FlotaleWorkflow
fun addWorkflow(
    builder: WorkflowDataBuilder.() -> Unit,
) = WorkflowDataBuilder()
    .apply(builder)

@FlotaleWorkflow
fun stage(
    builder: StageDataBuilder.() -> Unit,
) =
    StageDataBuilder()
        .apply(builder)
        .build()

@FlotaleWorkflow
fun task(
    builder: TaskDataBuilder.() -> Unit,
) =
    TaskDataBuilder()
        .apply(builder)
        .build()

@FlotaleWorkflow
fun action(
    builder: ActionDataBuilder.() -> Unit,
) =
    ActionDataBuilder()
        .apply(builder)
        .build()

@FlotaleWorkflow
infix fun FlotaleDomainEngine.persistWorkflow(
    builder: FlotaleWorkflowsBuilder.() -> Unit,
) {
    val commands: List<Command> = buildList {
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
                        registerTask(stage.key, TaskData(task.key, task.name, task.terminal), true)
                    }
                }
                stage.tasks
                    .forEach { task ->
                        add {
                            registerTask(stage.key, TaskData(task.key, task.name, task.terminal))
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
                            registerTask(stage.key, TaskData(task.key, task.name, task.terminal), true)
                        }
                    }

                    stage.tasks
                        .forEach { task ->
                            add {
                                registerTask(stage.key, TaskData(task.key, task.name, task.terminal))
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
    commands.forEach(Command::invoke)
}