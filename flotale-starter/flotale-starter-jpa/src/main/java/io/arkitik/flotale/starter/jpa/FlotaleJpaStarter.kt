package io.arkitik.flotale.starter.jpa

import io.arkitik.flotale.protocol.transactional.FlotaleTransactionalExecutor
import io.arkitik.flotale.starter.jpa.functions.DefaultJpaFlotaleTransactionalExecutor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

/**
 * @author Ibrahim Al-Tamimi 
 * @since 21:48, Tuesday, 05/05/2026
 **/
@Configuration
class FlotaleJpaStarter {
    @Bean
    @ConditionalOnMissingBean
    fun defaultJpaFlotaleTransactionalExecutor(
        platformTransactionManager: PlatformTransactionManager,
    ): FlotaleTransactionalExecutor =
        DefaultJpaFlotaleTransactionalExecutor(
            platformTransactionManager = platformTransactionManager
        )
}