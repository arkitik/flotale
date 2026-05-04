package io.arkitik.flotale.action.operation.roles

import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.operation.errors.FlotaleActionErrors
import io.arkitik.flotale.action.store.query.ActionStoreQuery
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:13 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class ActionDuplicationRole(
    private val actionStoreQuery: ActionStoreQuery,
) : OperationRole<String, Unit> {
    private val statuses = listOf(ActionStatus.ACTIVE)

    override fun String.operateRole() {
        if (actionStoreQuery.existByKeyAndStatusIn(this, statuses)) {
            throw FlotaleActionErrors.ACTION_ALREADY_EXIST.unprocessableEntity()
        }
    }
}
