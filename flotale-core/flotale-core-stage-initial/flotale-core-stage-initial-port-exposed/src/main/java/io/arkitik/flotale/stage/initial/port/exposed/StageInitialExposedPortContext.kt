package io.arkitik.flotale.stage.initial.port.exposed

import io.arkitik.flotale.stage.initial.adapter.exposed.ExposedStageInitialStore
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StageInitialExposedPortContext {

    @Bean
    fun stageInitialStore(
        @Autowired(required = false) database: Database?,
    ): StageInitialStore = ExposedStageInitialStore(database)
}
