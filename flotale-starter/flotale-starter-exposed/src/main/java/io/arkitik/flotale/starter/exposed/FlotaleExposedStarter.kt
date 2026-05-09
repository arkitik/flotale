package io.arkitik.flotale.starter.exposed

import io.arkitik.flotale.protocol.transactional.FlotaleTransactionalExecutor
import io.arkitik.flotale.starter.exposed.functions.DefaultExposedFlotaleTransactionalExecutor
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Ibrahim Al-Tamimi 
 * @since 21:48, Tuesday, 05/05/2026
 **/
@Configuration
class FlotaleExposedStarter {
    @Bean
    @ConditionalOnMissingBean
    fun defaultExposedFlotaleTransactionalExecutor(
        @Autowired(required = false) database: Database?,
    ): FlotaleTransactionalExecutor =
        DefaultExposedFlotaleTransactionalExecutor(
            database = database
        )
}