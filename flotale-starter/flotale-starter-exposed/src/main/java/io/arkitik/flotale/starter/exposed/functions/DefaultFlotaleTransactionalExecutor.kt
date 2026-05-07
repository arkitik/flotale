package io.arkitik.flotale.starter.exposed.functions

import io.arkitik.flotale.protocol.transactional.FlotaleTransactionalExecutor
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import org.jetbrains.exposed.v1.jdbc.Database

/**
 * @author Ibrahim Al-Tamimi 
 * @since 21:49, Tuesday, 05/05/2026
 **/
internal class DefaultFlotaleTransactionalExecutor(
    private val database: Database?,
) : FlotaleTransactionalExecutor {
    override fun <T> runOnTransaction(block: () -> T) =
        ensureInTransaction(database) {
            block()
        }
}