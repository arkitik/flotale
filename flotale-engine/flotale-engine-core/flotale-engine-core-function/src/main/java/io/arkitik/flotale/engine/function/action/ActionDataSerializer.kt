package io.arkitik.flotale.engine.function.action

/**
 * @author Ibrahim Al-Tamimi 
 * @since 20:33, Thursday, 14/05/2026
 **/
interface ActionDataSerializer {
    fun serialize(data: Map<String, Any>): ByteArray
    fun deserialize(data: ByteArray): Map<String, Any>
}