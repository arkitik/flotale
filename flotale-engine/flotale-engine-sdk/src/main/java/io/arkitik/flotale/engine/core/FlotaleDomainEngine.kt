package io.arkitik.flotale.engine.core

import io.arkitik.flotale.engine.core.dto.ActionData
import io.arkitik.flotale.engine.core.dto.StageData
import io.arkitik.flotale.engine.core.dto.TaskData
import io.arkitik.flotale.engine.core.dto.WorkflowData

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:34 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleDomainEngine {
    fun registerWorkflow(workflow: WorkflowData)
    fun registerStage(workflowKey: String, stage: StageData) =
        registerStage(workflowKey, stage, false)

    fun registerStage(workflowKey: String, stage: StageData, initialStage: Boolean)
    fun registerTask(stageKey: String, task: TaskData) = registerTask(stageKey, task, false)
    fun registerTask(stageKey: String, task: TaskData, initialTask: Boolean)
    fun addAction(taskKey: String, action: ActionData)

    fun deleteWorkflow(key: String)
    fun deleteStage(key: String)
    fun deleteTask(key: String)
    fun deleteAction(key: String)
}
