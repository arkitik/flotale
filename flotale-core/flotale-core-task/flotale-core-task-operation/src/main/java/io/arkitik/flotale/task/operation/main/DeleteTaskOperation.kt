package io.arkitik.flotale.task.operation.main

import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.domain.embedded.TaskStatus
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.delete
import io.arkitik.radix.develop.store.storeUpdaterWithUpdate

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:29 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class DeleteTaskOperation(
    private val taskStore: TaskStore,
    private val taskInitialStore: TaskInitialStore,
) : Operation<TaskDomain, Unit> {
    override fun TaskDomain.operate() {
        with(taskStore) {
            storeUpdaterWithUpdate(identityUpdater()) {
                TaskStatus.DELETED.status()
                update()
            }
        }.let {
            taskInitialStore.storeQuery.findByTask(it)
        }?.also(taskInitialStore::delete)
    }
}
