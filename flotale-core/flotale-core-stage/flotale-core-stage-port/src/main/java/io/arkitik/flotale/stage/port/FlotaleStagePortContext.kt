package io.arkitik.flotale.stage.port

import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.operation.FlotaleStageDomainSdkImpl
import io.arkitik.flotale.stage.sdk.FlotaleStageDomainSdk
import io.arkitik.flotale.stage.store.StageStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlotaleStagePortContext {
    @Bean
    fun flotaleStageDomainSdk(
        stageStore: StageStore,
        stageInitialStore: StageInitialStore,
    ): FlotaleStageDomainSdk =
        FlotaleStageDomainSdkImpl(
            stageStore = stageStore,
            stageInitialStore = stageInitialStore
        )
}
