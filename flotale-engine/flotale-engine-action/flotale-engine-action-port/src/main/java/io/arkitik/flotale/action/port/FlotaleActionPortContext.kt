package io.arkitik.flotale.action.port

import io.arkitik.flotale.action.operation.FlotaleActionDomainSdkImpl
import io.arkitik.flotale.action.sdk.FlotaleActionDomainSdk
import io.arkitik.flotale.action.store.ActionStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlotaleActionPortContext {
    @Bean
    fun flotaleActionDomainSdk(actionStore: ActionStore): FlotaleActionDomainSdk =
        FlotaleActionDomainSdkImpl(actionStore)
}
