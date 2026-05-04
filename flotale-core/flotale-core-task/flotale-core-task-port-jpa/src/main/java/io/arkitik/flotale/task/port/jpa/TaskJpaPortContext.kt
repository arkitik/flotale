package io.arkitik.flotale.task.port.jpa

import io.arkitik.flotale.task.adapter.TaskStoreImpl
import io.arkitik.flotale.task.adapter.repository.TaskRepository
import io.arkitik.flotale.task.store.TaskStore
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.task.adapter.repository")
@EntityScan("io.arkitik.flotale.task.entity")
class TaskJpaPortContext {
    @Bean
    fun taskStore(taskRepository: TaskRepository): TaskStore = TaskStoreImpl(taskRepository)
}
