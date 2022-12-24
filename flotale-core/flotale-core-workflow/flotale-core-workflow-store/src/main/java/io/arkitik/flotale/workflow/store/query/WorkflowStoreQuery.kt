package io.arkitik.flotale.workflow.store.query

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.radix.develop.store.query.StoreQuery

interface WorkflowStoreQuery : StoreQuery<String, WorkflowDomain> {
    fun existByKeyAndStatusIn(
        key: String,
        statuses: List<WorkflowStatus>,
    ): Boolean

    fun findByKeyAndNotDeleted(
        key: String,
    ): WorkflowDomain?
}
