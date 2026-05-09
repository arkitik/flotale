package io.arkitik.flotale.action.operation.main

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.store.storeUpdaterWithUpdate

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 3:29 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class DeleteActionOperation(
    private val actionStore: ActionStore,
) : Operation<ActionDomain, Unit> {
    override fun ActionDomain.operate() {
        with(actionStore) {
            storeUpdaterWithUpdate(identityUpdater()) {
                ActionStatus.DELETED.actionStatus()
                update()
            }
        }

    }
}
