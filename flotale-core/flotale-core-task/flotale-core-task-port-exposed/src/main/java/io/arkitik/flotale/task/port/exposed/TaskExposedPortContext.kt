package io.arkitik.flotale.task.port.exposed

import io.arkitik.flotale.task.adapter.exposed.ExposedTaskStore
import io.arkitik.flotale.task.store.TaskStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskExposedPortContext {

    @Bean
    fun taskStore(
        @Autowired(required = false) database: Database?,
    ): TaskStore = ExposedTaskStore(database)
}
