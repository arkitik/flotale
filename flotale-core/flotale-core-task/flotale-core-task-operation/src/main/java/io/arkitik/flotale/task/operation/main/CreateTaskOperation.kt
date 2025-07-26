package io.arkitik.flotale.task.operation.main

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.sdk.dto.CreateTaskDto
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.creatorWithInsert

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class CreateTaskOperation(
    private val taskStore: TaskStore,
    private val taskInitialStore: TaskInitialStore,
) : Operation<CreateTaskDto, TaskDomain> {
    override fun CreateTaskDto.operate() =
        with(taskStore) {
            creatorWithInsert(identityCreator()) {
                taskKey.taskKey()
                taskName.taskName()
                terminal.terminalTask()
                stage.stage()
                TaskStatus.ACTIVE.status()
            }
        }.also { taskDomain ->
            taskDomain.takeIf { initialTask }?.also { taskDomain ->
                with(taskInitialStore) {
                    creatorWithInsert(identityCreator()) {
                        taskDomain.task()
                        taskDomain.stage.stage()
                    }
                }
            }
        }
}
