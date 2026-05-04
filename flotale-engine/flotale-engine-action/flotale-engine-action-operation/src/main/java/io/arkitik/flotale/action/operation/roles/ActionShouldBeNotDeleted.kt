package io.arkitik.flotale.action.operation.roles

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.domain.embedded.ActionStatus
import io.arkitik.flotale.action.operation.errors.FlotaleActionErrors
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:40 PM, 17 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal object ActionShouldBeNotDeleted : OperationRole<ActionDomain, Unit> {
    override fun ActionDomain.operateRole() {
        if (status == ActionStatus.DELETED) {
            throw FlotaleActionErrors.ACTION_DOES_NOT_EXIST.unprocessableEntity()
        }
    }
}
