package io.arkitik.flotale.workflow.store.creator

import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.flotale.workflow.domain.embedded.WorkflowStatus
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface WorkflowDomainCreator : StoreIdentityCreator<String, WorkflowDomain> {
    fun String.workflowKey(): WorkflowDomainCreator

    fun String.workflowName(): WorkflowDomainCreator

    fun WorkflowStatus.status(): WorkflowDomainCreator
}
