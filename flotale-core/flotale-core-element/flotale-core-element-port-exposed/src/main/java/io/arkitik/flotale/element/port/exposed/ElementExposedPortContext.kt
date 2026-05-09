package io.arkitik.flotale.element.port.exposed

import io.arkitik.flotale.element.adapter.exposed.ExposedElementStore
import io.arkitik.flotale.element.store.ElementStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElementExposedPortContext {

    @Bean
    fun elementStore(
        @Autowired(required = false) database: Database?,
    ): ElementStore = ExposedElementStore(database)
}
