package io.arkitik.flotale.stage.initial.store.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.initial.domain.StageInitialDomain
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface StageInitialDomainCreator : StoreIdentityCreator<String, StageInitialDomain> {
    fun WorkflowDomain.workflow(): StageInitialDomainCreator

    fun StageDomain.stage(): StageInitialDomainCreator
}
