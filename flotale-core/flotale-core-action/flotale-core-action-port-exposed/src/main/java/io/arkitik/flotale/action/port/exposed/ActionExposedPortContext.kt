package io.arkitik.flotale.action.port.exposed

import io.arkitik.flotale.action.adapter.exposed.ExposedActionStore
import io.arkitik.flotale.action.store.ActionStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActionExposedPortContext {

    @Bean
    fun actionStore(
        @Autowired(required = false) database: Database?,
    ): ActionStore = ExposedActionStore(database)
}
