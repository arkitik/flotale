package io.arkitik.flotale.stage.initial.port.jpa

import io.arkitik.flotale.stage.initial.adapter.StageInitialStoreImpl
import io.arkitik.flotale.stage.initial.adapter.repository.StageInitialRepository
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.stage.initial.adapter.repository")
@EntityScan("io.arkitik.flotale.stage.initial.entity")
class StageInitialJpaPortContext {
    @Bean
    fun stageInitialStore(stageInitialRepository: StageInitialRepository): StageInitialStore =
        StageInitialStoreImpl(stageInitialRepository)
}
