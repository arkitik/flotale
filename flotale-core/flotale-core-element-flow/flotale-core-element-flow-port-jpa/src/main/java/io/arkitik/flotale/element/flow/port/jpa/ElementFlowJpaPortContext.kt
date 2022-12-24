package io.arkitik.flotale.element.flow.port.jpa

import io.arkitik.flotale.element.flow.adapter.ElementFlowStoreImpl
import io.arkitik.flotale.element.flow.adapter.repository.ElementFlowRepository
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import org.springframework.`data`.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.`annotation`.Bean
import org.springframework.context.`annotation`.Configuration

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.element.flow.adapter.repository")
@EntityScan("io.arkitik.flotale.element.flow.entity")
class ElementFlowJpaPortContext {
    @Bean
    fun elementFlowStore(elementFlowRepository: ElementFlowRepository): ElementFlowStore =
        ElementFlowStoreImpl(elementFlowRepository)
}
