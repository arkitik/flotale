package io.arkitik.flotale.element.port

import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.operation.FlotaleElementDomainSdkImpl
import io.arkitik.flotale.element.sdk.FlotaleElementDomainSdk
import io.arkitik.flotale.element.store.ElementStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:37 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@Configuration
class FlotaleElementPortContext {
    @Bean
    fun flotaleElementDomainSdk(
        elementStore: ElementStore,
        elementFlowStore: ElementFlowStore,
    ): FlotaleElementDomainSdk = FlotaleElementDomainSdkImpl(elementStore, elementFlowStore)
}
