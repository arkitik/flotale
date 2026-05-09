package io.arkitik.flotale.starter.jpa.functions

import io.arkitik.flotale.protocol.transactional.FlotaleTransactionalExecutor
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

/**
 * @author Ibrahim Al-Tamimi 
 * @since 21:49, Tuesday, 05/05/2026
 **/
internal class DefaultJpaFlotaleTransactionalExecutor(
    platformTransactionManager: PlatformTransactionManager,
) : FlotaleTransactionalExecutor {
    private val transactionTemplate = TransactionTemplate(platformTransactionManager)
    override fun <T> runOnTransaction(block: () -> T) =
        transactionTemplate.execute { block() }
}