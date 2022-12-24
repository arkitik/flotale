package io.arkitik.flotale.action.port.jpa

import io.arkitik.flotale.action.adapter.ActionStoreImpl
import io.arkitik.flotale.action.adapter.repository.ActionRepository
import io.arkitik.flotale.action.store.ActionStore
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.action.adapter.repository")
@EntityScan("io.arkitik.flotale.action.entity")
class ActionJpaPortContext {
    @Bean
    fun actionStore(actionRepository: ActionRepository): ActionStore =
        ActionStoreImpl(actionRepository)
}
