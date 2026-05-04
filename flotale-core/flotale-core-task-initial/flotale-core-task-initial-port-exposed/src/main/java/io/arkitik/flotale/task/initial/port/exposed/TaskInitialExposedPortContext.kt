package io.arkitik.flotale.task.initial.port.exposed

import io.arkitik.flotale.task.initial.adapter.exposed.ExposedTaskInitialStore
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskInitialExposedPortContext {

    @Bean
    fun taskInitialStore(
        @Autowired(required = false) database: Database?,
    ): TaskInitialStore = ExposedTaskInitialStore(database)
}
