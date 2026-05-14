package io.arkitik.flotale.plugin.serializer.jackson.units

import io.arkitik.flotale.engine.function.action.ActionDataSerializer
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue

/**
 * @author Ibrahim Al-Tamimi 
 * @since 20:38, Thursday, 14/05/2026
 **/
internal class JacksonActionDataSerializer(
    private val objectMapper: ObjectMapper,
) : ActionDataSerializer {
    override fun serialize(data: Map<String, Any>): ByteArray {
        return objectMapper.writeValueAsBytes(data)
    }

    override fun deserialize(data: ByteArray): Map<String, Any> {
        return objectMapper.readValue(data)
    }
}