package io.arkitik.flotale.task.port

import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.operation.FlotaleTaskDomainSdkImpl
import io.arkitik.flotale.task.sdk.FlotaleTaskDomainSdk
import io.arkitik.flotale.task.store.TaskStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:45 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@Configuration
class FlotaleTaskPortContext {
    @Bean
    fun flotaleTaskDomainSdk(
        taskStore: TaskStore,
        taskInitialStore: TaskInitialStore,
    ): FlotaleTaskDomainSdk =
        FlotaleTaskDomainSdkImpl(
            taskStore = taskStore,
            taskInitialStore = taskInitialStore,
        )
}
