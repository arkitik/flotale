package io.arkitik.flotale.stage.store.creator

import io.arkitik.flotale.stage.domain.StageDomain
import io.arkitik.flotale.stage.domain.embedded.StageStatus
import io.arkitik.flotale.workflow.domain.WorkflowDomain
import io.arkitik.radix.develop.store.creator.StoreIdentityCreator

interface StageDomainCreator : StoreIdentityCreator<String, StageDomain> {
    fun WorkflowDomain.workflow(): StageDomainCreator

    fun String.stageKey(): StageDomainCreator

    fun String.stageName(): StageDomainCreator

    fun StageStatus.status(): StageDomainCreator
}
