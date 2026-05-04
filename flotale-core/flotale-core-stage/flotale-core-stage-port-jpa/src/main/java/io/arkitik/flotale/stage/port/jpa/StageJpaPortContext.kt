package io.arkitik.flotale.stage.port.jpa

import io.arkitik.flotale.stage.adapter.StageStoreImpl
import io.arkitik.flotale.stage.adapter.repository.StageRepository
import io.arkitik.flotale.stage.store.StageStore
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.stage.adapter.repository")
@EntityScan("io.arkitik.flotale.stage.entity")
class StageJpaPortContext {
    @Bean
    fun stageStore(stageRepository: StageRepository): StageStore =
        StageStoreImpl(stageRepository)
}
