package io.arkitik.flotale.protocol.transactional

/**
 * @author Ibrahim Al-Tamimi 
 * @since 21:47, Tuesday, 05/05/2026
 **/
typealias TxCommand<T> = () -> T

interface FlotaleTransactionalExecutor {
    fun <T> runOnTransaction(block: TxCommand<T>): T
}

fun FlotaleTransactionalExecutor.executeCommand(command: TxCommand<Unit>) {
    runOnTransaction(command)
}