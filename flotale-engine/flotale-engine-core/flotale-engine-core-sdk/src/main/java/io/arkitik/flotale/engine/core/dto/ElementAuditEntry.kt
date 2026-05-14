package io.arkitik.flotale.engine.core.dto

import java.time.LocalDateTime

data class ElementAuditEntry(
    val actionKey: String,
    val actionName: String,
    val fromTask: ReferenceData,
    val toTask: ReferenceData,
    val executedBy: String,
    val executedAt: LocalDateTime,
    val executionData: Map<String, Any>? = null,
)
