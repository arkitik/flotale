package io.arkitik.flotale.element.port.jpa

import io.arkitik.flotale.element.adapter.ElementStoreImpl
import io.arkitik.flotale.element.adapter.repository.ElementRepository
import io.arkitik.flotale.element.store.ElementStore
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("io.arkitik.flotale.element.adapter.repository")
@EntityScan("io.arkitik.flotale.element.entity")
class ElementJpaPortContext {
    @Bean
    fun elementStore(elementRepository: ElementRepository): ElementStore =
        ElementStoreImpl(elementRepository)
}
