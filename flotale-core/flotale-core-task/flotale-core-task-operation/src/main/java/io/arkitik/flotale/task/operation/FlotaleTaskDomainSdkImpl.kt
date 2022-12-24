package io.arkitik.flotale.task.operation

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.operation.main.CreateTaskOperation
import io.arkitik.flotale.task.operation.main.DeleteTaskOperation
import io.arkitik.flotale.task.operation.main.FindTaskOperation
import io.arkitik.flotale.task.operation.main.InitialStageTaskOperation
import io.arkitik.flotale.task.operation.main.StageTasksOperation
import io.arkitik.flotale.task.operation.roles.StageShouldNotHasAnotherInitialTask
import io.arkitik.flotale.task.operation.roles.TaskDuplicationRole
import io.arkitik.flotale.task.operation.roles.TaskShouldBeNotDeleted
import io.arkitik.flotale.task.sdk.FlotaleTaskDomainSdk
import io.arkitik.flotale.task.sdk.dto.CreateTaskDto
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.operation.ext.operateRole
import io.arkitik.radix.develop.operation.ext.operationBuilder

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:35 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleTaskDomainSdkImpl(
    taskStore: TaskStore,
    taskInitialStore: TaskInitialStore,
) : FlotaleTaskDomainSdk {
    private val stageShouldNotHasAnotherInitialTask =
        StageShouldNotHasAnotherInitialTask(taskInitialStore.storeQuery)

    private val taskDuplicationRole = TaskDuplicationRole(taskStore.storeQuery)

    override val createTask: Operation<CreateTaskDto, Unit> =
        operationBuilder {
            install {
                taskDuplicationRole
                    .operateRole(taskKey)
            }

            install {
                stageShouldNotHasAnotherInitialTask.takeIf { initialTask }?.operateRole(stage)
            }

            mainOperation(
                CreateTaskOperation(
                    taskStore = taskStore,
                    taskInitialStore = taskInitialStore
                )
            )
        }

    override val findTask: Operation<String, TaskDomain> =
        operationBuilder {
            mainOperation(FindTaskOperation(taskStore.storeQuery))
        }

    override val deleteTask: Operation<TaskDomain, Unit> =
        operationBuilder {
            install(TaskShouldBeNotDeleted)
            mainOperation(
                DeleteTaskOperation(
                    taskStore,
                    taskInitialStore
                )
            )
        }
    override val stageTasks: Operation<StageDomain, List<TaskDomain>> =
        operationBuilder {
            mainOperation(StageTasksOperation(taskStore.storeQuery))
        }

    override val initialStageTask: Operation<StageDomain, TaskDomain> =
        operationBuilder {
            mainOperation(InitialStageTaskOperation(taskInitialStore.storeQuery))
        }
}
