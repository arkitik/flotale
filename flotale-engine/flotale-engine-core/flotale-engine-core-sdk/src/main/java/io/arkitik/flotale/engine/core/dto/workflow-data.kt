package io.arkitik.flotale.engine.core.dto

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:37 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */

data class WorkflowData(
    val key: String,
    val name: String,
    val initialStage: StageData? = null,
    val stages: List<StageData> = listOf(),
)


data class StageData(
    val key: String,
    val name: String,
    val initialTask: TaskData? = null,
    val tasks: List<TaskData> = listOf(),
)

data class TaskData(
    val key: String,
    val name: String,
    val terminal: Boolean,
    val actions: List<ActionData> = listOf(),
)


data class ActionData(
    val key: String,
    val name: String,
    val destinationTaskKey: String,
    val formAction: Boolean,
    val actionMessage: String?,
    val actionColor: String,
    val actionHint: String?,
    val actionOutlined: Boolean,
    val successExecutionMessage: String?,
    val failedExecutionMessage: String?,
)
