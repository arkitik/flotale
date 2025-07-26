package io.arkitik.flotale.task.sdk

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.sdk.dto.CreateTaskDto
import io.arkitik.radix.develop.operation.Operation

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 12:10 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleTaskDomainSdk {
    val createTask: Operation<CreateTaskDto, TaskDomain>
    val findTask: Operation<String, TaskDomain>
    val deleteTask: Operation<TaskDomain, Unit>

    val stageTasks: Operation<StageDomain, List<TaskDomain>>
    val initialStageTask: Operation<StageDomain, TaskDomain>
}
