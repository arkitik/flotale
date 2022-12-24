package io.arkitik.flotale.workflow.store.updater

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.radix.develop.store.updater.StoreIdentityUpdater

interface WorkflowDomainUpdater : StoreIdentityUpdater<String, WorkflowDomain> {
    fun WorkflowStatus.status(): WorkflowDomainUpdater
}
