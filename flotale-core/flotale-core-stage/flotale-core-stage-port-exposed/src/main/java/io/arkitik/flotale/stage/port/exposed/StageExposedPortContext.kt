package io.arkitik.flotale.stage.port.exposed

import io.arkitik.flotale.stage.adapter.exposed.ExposedStageStore
import io.arkitik.flotale.stage.store.StageStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StageExposedPortContext {

    @Bean
    fun stageStore(
        @Autowired(required = false) database: Database?,
    ): StageStore = ExposedStageStore(database)
}
