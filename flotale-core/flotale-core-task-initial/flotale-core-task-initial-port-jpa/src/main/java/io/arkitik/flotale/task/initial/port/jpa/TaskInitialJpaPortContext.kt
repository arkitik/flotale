package io.arkitik.flotale.task.initial.port.jpa

import io.arkitik.flotale.task.initial.adapter.TaskInitialStoreImpl
import io.arkitik.flotale.task.initial.adapter.repository.TaskInitialRepository
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.task.initial.adapter.repository")
@EntityScan("io.arkitik.flotale.task.initial.entity")
class TaskInitialJpaPortContext {
    @Bean
    fun taskInitialStore(taskInitialRepository: TaskInitialRepository): TaskInitialStore =
        TaskInitialStoreImpl(taskInitialRepository)
}
