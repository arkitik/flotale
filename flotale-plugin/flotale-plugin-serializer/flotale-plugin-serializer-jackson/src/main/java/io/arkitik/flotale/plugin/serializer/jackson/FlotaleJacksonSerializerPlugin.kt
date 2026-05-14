package io.arkitik.flotale.plugin.serializer.jackson

import io.arkitik.flotale.engine.function.action.ActionDataSerializer
import io.arkitik.flotale.plugin.serializer.jackson.units.JacksonActionDataSerializer
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.ObjectMapper

/**
 * @author Ibrahim Al-Tamimi 
 * @since 20:36, Thursday, 14/05/2026
 **/
@Configuration
class FlotaleJacksonSerializerPlugin {
    @Bean
    @ConditionalOnMissingBean
    fun actionDataSerializer(
        objectMapper: ObjectMapper,
    ): ActionDataSerializer =
        JacksonActionDataSerializer(
            objectMapper = objectMapper
        )
}