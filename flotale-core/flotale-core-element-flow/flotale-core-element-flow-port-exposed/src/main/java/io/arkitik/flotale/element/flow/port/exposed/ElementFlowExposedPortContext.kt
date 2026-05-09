package io.arkitik.flotale.element.flow.port.exposed

import io.arkitik.flotale.element.flow.adapter.exposed.ExposedElementFlowStore
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElementFlowExposedPortContext {

    @Bean
    fun elementFlowStore(
        @Autowired(required = false) database: Database?,
    ): ElementFlowStore = ExposedElementFlowStore(database)
}
